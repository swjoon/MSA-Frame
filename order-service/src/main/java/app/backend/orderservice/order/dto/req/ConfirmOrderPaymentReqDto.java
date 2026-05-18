package app.backend.orderservice.order.dto.req;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ConfirmOrderPaymentReqDto(
	@NotBlank
	String paymentKey,

	@NotNull
	@Min(0)
	Long amount,

	@NotBlank
	@Size(min = 6, max = 64)
	String orderId
) {
}
