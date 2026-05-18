package app.backend.paymentservice.payment.controller.internal;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.backend.paymentservice.infrastructure.client.payment.dto.request.PaymentRequest;
import app.backend.paymentservice.infrastructure.client.payment.gateway.PaymentGateway;
import app.backend.paymentservice.payment.dto.response.GetPaymentDto;
import app.backend.paymentservice.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/internal/payments")
@RequiredArgsConstructor
public class PaymentInternalController {

	private final PaymentService paymentService;

	private final PaymentGateway paymentGateway;

	@PostMapping("/{orderId}/confirmation")
	public GetPaymentDto confirmPayment(
		@PathVariable("orderId") final Long orderId,
		@RequestBody final PaymentRequest requestDto
	) {

		return paymentGateway.confirmAndCreatePayment(orderId, requestDto);
	}

}
