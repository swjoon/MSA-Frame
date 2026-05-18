package app.backend.paymentservice.payment.service;

import app.backend.paymentservice.infrastructure.client.payment.dto.request.PaymentRequest;
import app.backend.paymentservice.infrastructure.client.payment.dto.response.PaymentResponse;
import app.backend.paymentservice.payment.dto.response.GetPaymentDto;

public interface PaymentService {

	GetPaymentDto confirmPayment(Long orderId, PaymentResponse result);

	void createPayment(Long orderId, PaymentRequest requestDto);

	GetPaymentDto getPayment(Long paymentId);

	GetPaymentDto getPaymentWithKey(String paymentKey);

	GetPaymentDto getPaymentByOrderId(Long orderId);

}
