package app.backend.paymentservice.infrastructure.client.payment;

import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import app.backend.paymentservice.infrastructure.client.payment.dto.request.PaymentCancelRequest;
import app.backend.paymentservice.infrastructure.client.payment.dto.request.PaymentRequest;
import app.backend.paymentservice.infrastructure.client.payment.dto.response.PaymentCancelResponse;
import app.backend.paymentservice.infrastructure.client.payment.dto.response.PaymentResponse;
import app.backend.paymentservice.payment.exception.PaymentErrorCode;
import app.backend.paymentservice.payment.exception.PaymentException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentHttpClient {

	private static final String PAYMENT_URL = "https://payment.postodos.com/api/v1/payments";

	private final RestTemplate restTemplate;
	private final ObjectMapper objectMapper;

	public PaymentResponse sendPaymentConfirmRequest(final PaymentRequest request) {
		String confirmUrl = PAYMENT_URL + "/confirm";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<PaymentRequest> entity = new HttpEntity<>(request, headers);

		try {
			ResponseEntity<PaymentResponse> responseEntity = restTemplate.exchange(
				confirmUrl,
				HttpMethod.POST,
				entity,
				PaymentResponse.class
			);

			return responseEntity.getBody();
		} catch (HttpStatusCodeException e) {
			handleApiError(e);

			throw new PaymentException(PaymentErrorCode.PAYMENT_API_ERROR);
		}
	}

	public PaymentResponse getPaymentInfo(final String paymentKey) {
		String getPaymentInfoUrl = PAYMENT_URL + "/" + paymentKey;

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> entity = new HttpEntity<>(headers);

		try {
			ResponseEntity<PaymentResponse> responseEntity = restTemplate.exchange(
				getPaymentInfoUrl,
				HttpMethod.GET,
				entity,
				PaymentResponse.class
			);

			return responseEntity.getBody();
		} catch (HttpStatusCodeException e) {
			handleApiError(e);

			throw new PaymentException(PaymentErrorCode.PAYMENT_API_ERROR);
		}

	}

	public PaymentCancelResponse cancelPayment(final String paymentKey, final String cancelReason, final Long amount) {
		String cancelUrl = PAYMENT_URL + "/" + paymentKey + "/cancel";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		PaymentCancelRequest cancelRequest = PaymentCancelRequest.from(cancelReason, amount);

		HttpEntity<PaymentCancelRequest> entity = new HttpEntity<>(cancelRequest, headers);

		try {
			ResponseEntity<PaymentCancelResponse> response = restTemplate.exchange(
				cancelUrl,
				HttpMethod.POST,
				entity,
				PaymentCancelResponse.class
			);
			log.info("결제 취소 완료: paymentKey={}, amount={}", paymentKey, amount);

			return response.getBody();
		} catch (HttpStatusCodeException e) {
			log.error("결제 취소 실패: {}", e.getResponseBodyAsString());

			throw new PaymentException(PaymentErrorCode.PAYMENT_API_CANCEL_FAILED);
		}
	}

	private void handleApiError(HttpStatusCodeException e) {
		try {
			Map<String, Object> errorResponse = objectMapper
				.readValue(e.getResponseBodyAsString(), new TypeReference<>() {
				});

			log.error("Payment API Error: code={}, message={}", errorResponse.get("code"),
				errorResponse.get("message"));
		} catch (Exception ex) {
			log.error("Failed to parse Payment API error", ex);
		}
	}
}
