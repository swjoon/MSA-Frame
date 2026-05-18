package app.backend.orderservice.infrastructure.client.error;

public class UnknownInternalServiceException extends InternalServiceException {

	public UnknownInternalServiceException(
		final String code,
		final String message,
		final int status,
		final String traceId
	) {
		super("unknown-service", code, message, status, traceId);
	}
}
