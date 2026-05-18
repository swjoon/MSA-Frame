package app.backend.paymentservice.payment.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import app.backend.paymentservice.payment.entity.Payment;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PaymentRepositoryImpl implements PaymentRepository {

	private final PaymentJpaRepository jpaRepository;

	@Override
	public void createPayment(final Payment payment) {

		jpaRepository.save(payment);
	}

	@Override
	public Optional<Payment> findPaymentById(final Long paymentId) {

		return jpaRepository.findById(paymentId);
	}

	@Override
	public Optional<Payment> findPaymentByOrderId(final Long orderId) {

		return jpaRepository.findByOrderId(orderId);
	}

	@Override
	public Optional<Payment> findPaymentByPaymentKey(final String paymentKey) {

		return jpaRepository.findByPaymentKey(paymentKey);
	}

}
