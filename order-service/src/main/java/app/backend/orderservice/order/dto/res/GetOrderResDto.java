package app.backend.orderservice.order.dto.res;

import java.time.LocalDateTime;

import app.backend.orderservice.order.entity.Order;
import app.backend.orderservice.order.entity.OrderStatus;

public record GetOrderResDto(
	Long orderId,
	Long memberId,
	Long itemId,
	String orderNumber,
	int stock,
	Long totalPrice,
	LocalDateTime orderDateTime,
	OrderStatus status
) {
	public static GetOrderResDto from(final Order order) {

		return new GetOrderResDto(
			order.getId(),
			order.getMemberId(),
			order.getItemId(),
			order.getOrderNumber(),
			order.getStock(),
			order.getTotalPrice(),
			order.getOrderDateTime(),
			order.getStatus()
		);
	}
}
