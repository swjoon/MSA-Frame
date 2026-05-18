package app.backend.orderservice.infrastructure.client.payment.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import app.backend.orderservice.infrastructure.client.config.FeignErrorDecoderConfig;
import app.backend.orderservice.infrastructure.client.payment.dto.res.ConfirmPaymentResDto;
import app.backend.orderservice.order.dto.req.ConfirmOrderPaymentReqDto;

@FeignClient(
	name = "PAYMENT-SERVICE",
	configuration = FeignErrorDecoderConfig.class
)
public interface PaymentServiceClient {

	@PostMapping("/internal/payments/{orderId}/confirmation")
	ConfirmPaymentResDto confirmPayment(
		@PathVariable("orderId") Long orderId,
		@RequestBody ConfirmOrderPaymentReqDto requestDto
	);

}
