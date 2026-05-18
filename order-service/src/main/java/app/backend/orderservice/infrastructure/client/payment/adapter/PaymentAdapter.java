package app.backend.orderservice.infrastructure.client.payment.adapter;

import org.springframework.stereotype.Service;

import app.backend.orderservice.infrastructure.client.constants.InternalServiceType;
import app.backend.orderservice.infrastructure.client.error.InternalFeignCallExecutor;
import app.backend.orderservice.infrastructure.client.payment.dto.res.ConfirmPaymentResDto;
import app.backend.orderservice.infrastructure.client.payment.service.PaymentServiceClient;
import app.backend.orderservice.order.dto.req.ConfirmOrderPaymentReqDto;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentAdapter {

	private final PaymentServiceClient paymentServiceClient;
	private final InternalFeignCallExecutor feignCallExecutor;

	public ConfirmPaymentResDto confirmPayment(final Long orderId, final ConfirmOrderPaymentReqDto requestDto) {

		return feignCallExecutor.execute(
			InternalServiceType.PAYMENT,
			() -> paymentServiceClient.confirmPayment(orderId, requestDto)
		);
	}
}
