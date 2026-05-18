package app.backend.orderservice.infrastructure.kafka.event.dto.request;

public record OrderItemReleasedEvent(
	Long itemId,
	int stock
) {
	public static OrderItemReleasedEvent from(Long itemId, int stock) {
		return new OrderItemReleasedEvent(itemId, stock);
	}
}
