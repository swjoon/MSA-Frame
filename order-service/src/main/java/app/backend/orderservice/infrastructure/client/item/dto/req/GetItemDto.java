package app.backend.orderservice.infrastructure.client.item.dto.req;

public record GetItemDto(
	Long itemId,
	String name,
	String description,
	Long price,
	int stock
) {
}
