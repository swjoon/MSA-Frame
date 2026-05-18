package app.backend.itemservice.item.dto.req;

public record UpdateItemDto(
	String name,
	String description,
	Long price
) {
}
