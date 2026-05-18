package app.backend.paymentservice.infrastructure.client.payment.gateway;

import app.backend.paymentservice.infrastructure.client.payment.dto.request.PaymentRequest;
import app.backend.paymentservice.infrastructure.client.payment.dto.response.PaymentResponse;
import app.backend.paymentservice.payment.dto.response.GetPaymentDto;

public interface PaymentGateway {

	GetPaymentDto confirmAndCreatePayment(Long orderId, PaymentRequest requestDto);

	PaymentResponse getPayment(String paymentKey);
}
