package app.backend.orderservice.order.entity;

import lombok.Getter;

@Getter
public enum OrderStatus {

	CREATED, PENDING, CONFIRMED, REJECTED, CANCELLED;

}
