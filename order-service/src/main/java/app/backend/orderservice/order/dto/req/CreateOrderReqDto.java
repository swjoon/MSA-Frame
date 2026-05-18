package app.backend.orderservice.order.dto.req;

public record CreateOrderReqDto(
	Long memberId,
	Long itemId,
	int stock,
	Long totalPrice
) {
}