package app.backend.orderservice.infrastructure.client.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum InternalServiceType {

	ITEM("item-service"),
	PAYMENT("payment-service"),
	UNKNOWN("unknown-service");

	private final String serviceName;

}
