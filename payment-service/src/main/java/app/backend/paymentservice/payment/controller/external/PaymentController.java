package app.backend.paymentservice.payment.controller.external;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.backend.paymentservice.global.response.ApiResponse;
import app.backend.paymentservice.payment.dto.response.GetPaymentDto;
import app.backend.paymentservice.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

	private final PaymentService paymentService;

	@PostMapping
	public ApiResponse<Void> createPayment() {

		return null;
	}

	@GetMapping("/{paymentKey}")
	public ApiResponse<GetPaymentDto> getPayment(@PathVariable("paymentKey") String paymentKey) {

		GetPaymentDto res = paymentService.getPaymentWithKey(paymentKey);

		return ApiResponse.of(true, HttpStatus.OK, "결제내역 조회", res);
	}

}
