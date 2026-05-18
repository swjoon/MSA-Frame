package app.backend.orderservice.infrastructure.client.error;

import org.springframework.stereotype.Component;

import app.backend.orderservice.infrastructure.client.constants.InternalServiceType;
import app.backend.orderservice.infrastructure.client.item.exception.ItemInternalException;
import app.backend.orderservice.infrastructure.client.payment.exception.PaymentInternalException;

@Component
public class InternalServiceExceptionFactory {

	public InternalServiceException create(
		InternalServiceType serviceType,
		String code,
		String message,
		int status,
		String traceId
	) {
		return switch (serviceType) {
			case ITEM -> new ItemInternalException(code, message, status, traceId);
			case PAYMENT -> new PaymentInternalException(code, message, status, traceId);
			case UNKNOWN -> new UnknownInternalServiceException(code, message, status, traceId);
		};
	}

}
