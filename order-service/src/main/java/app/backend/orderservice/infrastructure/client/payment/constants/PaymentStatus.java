package app.backend.orderservice.infrastructure.client.payment.constants;

import lombok.Getter;

@Getter
public enum PaymentStatus {

	READY, DONE, CANCELED, ABORTED;

}
