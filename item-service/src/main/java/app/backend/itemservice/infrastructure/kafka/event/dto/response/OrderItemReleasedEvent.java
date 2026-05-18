package app.backend.itemservice.infrastructure.kafka.event.dto.response;

public record OrderItemReleasedEvent(
	Long itemId,
	int stock
) {
	public static OrderItemReleasedEvent from(Long itemId, int stock) {
		return new OrderItemReleasedEvent(itemId, stock);
	}
}
