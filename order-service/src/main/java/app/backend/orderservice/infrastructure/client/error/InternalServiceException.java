package app.backend.orderservice.infrastructure.client.error;

import lombok.Getter;

@Getter
public abstract class InternalServiceException extends RuntimeException {

	private final String serviceName;
	private final String code;
	private final int status;
	private final String traceId;

	protected InternalServiceException(
		final String serviceName,
		final String code,
		final String message,
		final int status,
		final String traceId
	) {
		super(message);
		this.serviceName = serviceName;
		this.code = code;
		this.status = status;
		this.traceId = traceId;
	}

}
