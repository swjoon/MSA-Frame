package app.backend.orderservice.order.orchestrator;

import org.springframework.stereotype.Service;

import app.backend.orderservice.infrastructure.client.item.adapter.ItemAdapter;
import app.backend.orderservice.infrastructure.client.item.dto.req.GetItemDto;
import app.backend.orderservice.infrastructure.client.item.dto.res.UpdateItemStockDto;
import app.backend.orderservice.infrastructure.client.item.entity.CalculationType;
import app.backend.orderservice.infrastructure.client.item.exception.ItemInternalException;
import app.backend.orderservice.infrastructure.client.payment.adapter.PaymentAdapter;
import app.backend.orderservice.infrastructure.client.payment.constants.PaymentStatus;
import app.backend.orderservice.infrastructure.client.payment.dto.res.ConfirmPaymentResDto;
import app.backend.orderservice.infrastructure.client.payment.exception.PaymentInternalException;
import app.backend.orderservice.order.dto.req.ConfirmOrderPaymentReqDto;
import app.backend.orderservice.order.dto.req.CreateOrderReqDto;
import app.backend.orderservice.order.dto.res.CreateOrderResDto;
import app.backend.orderservice.order.dto.res.GetOrderWithPaymentDto;
import app.backend.orderservice.order.entity.Order;
import app.backend.orderservice.order.event.OrderEventService;
import app.backend.orderservice.order.exception.OrderErrorCode;
import app.backend.orderservice.order.exception.OrderException;
import app.backend.orderservice.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderOrchestratorImpl implements OrderOrchestrator {

	private final ItemAdapter itemAdapter;
	private final PaymentAdapter paymentAdapter;

	private final OrderService orderService;
	private final OrderEventService orderEventService;

	@Override
	public CreateOrderResDto createOrder(final CreateOrderReqDto createOrderReqDto) {

		GetItemDto item = null;

		try {
			item = itemAdapter.getItem(createOrderReqDto.itemId());

		} catch (ItemInternalException ex) {
			throw new OrderException(OrderErrorCode.ITEM_NOT_FOUND);
		}

		if (item.stock() < createOrderReqDto.stock()) {
			throw new OrderException(OrderErrorCode.ITEM_STOCK_SHORTAGE);
		}

		return orderService.createOrder(createOrderReqDto);
	}

	// Saga 패턴중 오케스트레이션 패턴으로 중앙 관리형으로 설계.
	@Override
	public GetOrderWithPaymentDto confirmOrderPayment(final Long orderId, final ConfirmOrderPaymentReqDto requestDto) {

		// 1. 주문정보 확인.
		Order order = orderService.getOrder(orderId);

		try {
			// 2. item 내부통신으로 item 정상 차감 확인
			itemAdapter.updateItemStock(
				order.getItemId(),
				UpdateItemStockDto.from(order.getStock(), CalculationType.MINUS)
			);

			// 3. payment 내부통신으로 외부 api 로 결제 프로세스 진행.
			ConfirmPaymentResDto paymentInfo = paymentAdapter.confirmPayment(orderId, requestDto);

			Order updateOrder;

			if (paymentInfo.status() == PaymentStatus.ABORTED) {

				updateOrder = orderEventService
					.rejectOrderWithItemIncreaseEvent(
						orderId,
						order.getItemId(),
						order.getStock()
					);
			} else if (paymentInfo.status() == PaymentStatus.DONE) {

				updateOrder = orderService.confirmOrder(orderId);
			} else {

				updateOrder = orderEventService
					.pendingOrderWithPaymentCheckEvent(
						orderId,
						requestDto.orderId(),
						requestDto.paymentKey()
					);
			}

			// 4. 주문 정보 수정 및 반환

			return GetOrderWithPaymentDto
				.from(
					updateOrder,
					paymentInfo
				);
		} catch (ItemInternalException ex) {
			log.warn(
				"Item 서비스 호출 실패. orderId={}, itemId={}, code={}, status={}, traceId={}",
				orderId,
				order.getItemId(),
				ex.getCode(),
				ex.getStatus(),
				ex.getTraceId(),
				ex
			);

			orderService.rejectOrder(orderId);

			throw new OrderException(OrderErrorCode.ITEM_STOCK_DEDUCT_FAILED);
		} catch (PaymentInternalException ex) {
			log.warn(
				"Payment 서비스 호출 실패. orderId={}, code={}, status={}, traceId={}",
				orderId,
				ex.getCode(),
				ex.getStatus(),
				ex.getTraceId(),
				ex
			);

			orderEventService.pendingOrderWithPaymentCheckEvent(orderId, requestDto.orderId(), requestDto.paymentKey());

			throw new OrderException(OrderErrorCode.PAYMENT_TIMEOUT);
		}
	}
}
