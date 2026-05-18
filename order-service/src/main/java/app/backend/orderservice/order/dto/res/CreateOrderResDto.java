package app.backend.orderservice.order.dto.res;

import app.backend.orderservice.order.entity.Order;
import app.backend.orderservice.order.entity.OrderStatus;

public record CreateOrderResDto(
	Long orderId,
	Long memberId,
	Long itemId,
	String orderNumber,
	int stock,
	Long totalPrice,
	OrderStatus status
) {

	public static CreateOrderResDto from(Order order) {
		return new CreateOrderResDto(
			order.getId(),
			order.getMemberId(),
			order.getItemId(),
			order.getOrderNumber(),
			order.getStock(),
			order.getTotalPrice(),
			order.getStatus()
		);
	}
}
