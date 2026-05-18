package app.backend.orderservice.order.event;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.backend.orderservice.infrastructure.kafka.constants.EventTopics;
import app.backend.orderservice.infrastructure.kafka.event.dto.request.OrderItemReleasedEvent;
import app.backend.orderservice.infrastructure.kafka.event.dto.request.PaymentCheckedEvent;
import app.backend.orderservice.infrastructure.kafka.outbox.service.OutboxEventService;
import app.backend.orderservice.order.entity.Order;
import app.backend.orderservice.order.service.OrderService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderEventServiceImpl implements OrderEventService {

	private final OrderService orderService;
	private final OutboxEventService outboxEventService;

	@Override
	@Transactional
	public Order rejectOrderWithItemIncreaseEvent(final Long orderId, final Long itemId, final int stock) {

		Order order = orderService.getOrder(orderId);

		order.reject();

		OrderItemReleasedEvent event = OrderItemReleasedEvent.from(itemId, stock);

		outboxEventService.publish(
			"Item",
			String.valueOf(itemId),
			"OrderItemReleaseRequested",
			EventTopics.ORDER_ITEM_RELEASE_REQUESTED,
			event
		);

		return order;
	}

	@Override
	@Transactional
	public Order pendingOrderWithPaymentCheckEvent(
		final Long orderId,
		final String orderNumber,
		final String paymentKey
	) {

		Order order = orderService.getOrder(orderId);

		order.pending();

		PaymentCheckedEvent event = PaymentCheckedEvent.from(orderId, orderNumber, paymentKey);

		outboxEventService.publish(
			"Payment",
			String.valueOf(orderId),
			"PaymentCheckRequired",
			EventTopics.PAYMENT_CHECK_REQUIRED,
			event
		);

		return order;
	}

}
