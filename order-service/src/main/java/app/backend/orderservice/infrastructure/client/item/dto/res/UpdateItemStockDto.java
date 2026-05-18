package app.backend.orderservice.infrastructure.client.item.dto.res;

import app.backend.orderservice.infrastructure.client.item.entity.CalculationType;

public record UpdateItemStockDto(
	int stock,
	CalculationType type
) {

	public static UpdateItemStockDto from(int stock, CalculationType type) {

		return new UpdateItemStockDto(stock, type);
	}
}
