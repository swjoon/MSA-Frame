package app.backend.itemservice.item.dto.req;

import app.backend.itemservice.item.entity.CalculationType;

public record UpdateItemStockDto(
	int stock,
	CalculationType type
) {
	public static UpdateItemStockDto from(int stock, CalculationType type) {
		return new UpdateItemStockDto(stock, type);
	}
}
