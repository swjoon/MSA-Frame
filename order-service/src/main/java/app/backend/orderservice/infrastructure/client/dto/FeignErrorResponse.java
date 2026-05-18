package app.backend.orderservice.infrastructure.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record FeignErrorResponse(
	boolean success,
	String code,
	String message,
	String traceId,
	Object data
) {
}