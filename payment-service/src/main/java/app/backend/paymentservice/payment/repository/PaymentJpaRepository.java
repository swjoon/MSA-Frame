package app.backend.paymentservice.payment.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.backend.paymentservice.payment.entity.Payment;

@Repository
public interface PaymentJpaRepository extends JpaRepository<Payment, Long> {

	Optional<Payment> findByOrderId(Long orderId);

	Optional<Payment> findByPaymentKey(String paymentKey);

}
