package app.backend.paymentservice.infrastructure.client.payment.gateway;

import org.springframework.stereotype.Service;

import app.backend.paymentservice.infrastructure.client.payment.PaymentHttpClient;
import app.backend.paymentservice.infrastructure.client.payment.dto.request.PaymentRequest;
import app.backend.paymentservice.infrastructure.client.payment.dto.response.PaymentResponse;
import app.backend.paymentservice.payment.dto.response.GetPaymentDto;
import app.backend.paymentservice.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentGatewayImpl implements PaymentGateway {

	private final PaymentService paymentService;

	private final PaymentHttpClient paymentHttpClient;

	@Override
	public GetPaymentDto confirmAndCreatePayment(final Long orderId, final PaymentRequest requestDto) {

		// 1. 결제 정보 사전저장 (DB uniqueKey 를 사용해서 멱등성 관리)
		paymentService.createPayment(orderId, requestDto);

		// 2. 외부 pg api
		PaymentResponse res = paymentHttpClient.sendPaymentConfirmRequest(requestDto);

		// 3. 결제 정보 업데이트
		return paymentService.confirmPayment(orderId, res);
	}

	@Override
	public PaymentResponse getPayment(final String paymentKey) {

		return paymentHttpClient.getPaymentInfo(paymentKey);
	}

}
