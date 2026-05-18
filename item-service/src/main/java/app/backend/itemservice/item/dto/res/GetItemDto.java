package app.backend.itemservice.item.dto.res;

import app.backend.itemservice.item.entity.Item;

public record GetItemDto(
	Long itemId,
	String name,
	String description,
	Long price,
	int stock
) {
	public static GetItemDto from(Item item) {
		return new GetItemDto(item.getId(), item.getName(), item.getDescription(), item.getPrice(), item.getStock());
	}
}
