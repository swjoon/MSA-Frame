package app.backend.orderservice.infrastructure.client.item.exception;

import app.backend.orderservice.infrastructure.client.error.InternalServiceException;

public class ItemInternalException extends InternalServiceException {

	public ItemInternalException(final String code, final String message, final int status, final String traceId) {
		super("item-service", code, message, status, traceId);
	}
}
