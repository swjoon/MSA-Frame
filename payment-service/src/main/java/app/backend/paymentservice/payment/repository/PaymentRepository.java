package app.backend.paymentservice.payment.repository;

import java.util.Optional;

import app.backend.paymentservice.payment.entity.Payment;

public interface PaymentRepository {

	void createPayment(Payment payment);

	Optional<Payment> findPaymentById(Long id);

	Optional<Payment> findPaymentByOrderId(Long orderId);

	Optional<Payment> findPaymentByPaymentKey(String paymentKey);
}
