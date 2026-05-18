package app.backend.paymentservice.payment.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.backend.paymentservice.infrastructure.client.payment.dto.request.PaymentRequest;
import app.backend.paymentservice.infrastructure.client.payment.dto.response.PaymentResponse;
import app.backend.paymentservice.payment.dto.response.GetPaymentDto;
import app.backend.paymentservice.payment.entity.Payment;
import app.backend.paymentservice.payment.exception.PaymentErrorCode;
import app.backend.paymentservice.payment.exception.PaymentException;
import app.backend.paymentservice.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

	private final PaymentRepository paymentRepository;

	@Override
	@Transactional
	public GetPaymentDto confirmPayment(Long orderId, PaymentResponse result) {

		Payment payment = getPaymentWithPaymentKey(result.getPaymentKey());

		payment.update(result.getMethod(), result.getStatus(), result.getApprovedAt());

		if (result.getCard() != null) {
			payment.updateCardInfo(result.getCard().getNumber(), result.getCard().getApproveNo());
		}

		if (result.getReceipt() != null) {
			payment.updateReceiptUrl(result.getReceipt().getUrl());
		}

		return GetPaymentDto.from(payment);
	}

	@Override
	@Transactional
	public void createPayment(final Long orderId, final PaymentRequest requestDto) {

		paymentRepository.createPayment(
			Payment.create(
				orderId,
				requestDto.orderId(),
				requestDto.paymentKey(),
				requestDto.amount()
			)
		);
	}

	@Override
	@Transactional(readOnly = true)
	public GetPaymentDto getPayment(final Long paymentId) {

		return GetPaymentDto.from(getPaymentWithPaymentId(paymentId));
	}

	@Override
	@Transactional(readOnly = true)
	public GetPaymentDto getPaymentWithKey(final String paymentKey) {

		return GetPaymentDto.from(getPaymentWithPaymentKey(paymentKey));
	}

	@Override
	@Transactional(readOnly = true)
	public GetPaymentDto getPaymentByOrderId(final Long orderId) {

		return GetPaymentDto.from(getPaymentWithOrderId(orderId));
	}

	private Payment getPaymentWithPaymentId(final Long paymentId) {

		return paymentRepository.findPaymentById(paymentId).orElseThrow(
			() -> new PaymentException(PaymentErrorCode.PAYMENT_NOT_FOUND)
		);
	}

	private Payment getPaymentWithOrderId(final Long orderId) {

		return paymentRepository.findPaymentByOrderId(orderId).orElseThrow(
			() -> new PaymentException(PaymentErrorCode.PAYMENT_NOT_FOUND)
		);
	}

	private Payment getPaymentWithPaymentKey(final String paymentKey) {

		return paymentRepository.findPaymentByPaymentKey(paymentKey).orElseThrow(
			() -> new PaymentException(PaymentErrorCode.PAYMENT_NOT_FOUND)
		);
	}

}
