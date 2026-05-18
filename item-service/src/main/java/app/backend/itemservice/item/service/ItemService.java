package app.backend.itemservice.item.service;

import app.backend.itemservice.item.dto.req.CreateItemDto;
import app.backend.itemservice.item.dto.req.UpdateItemDto;
import app.backend.itemservice.item.dto.res.GetItemDto;
import app.backend.itemservice.item.dto.req.UpdateItemStockDto;

public interface ItemService {

	Long createItem(CreateItemDto requestDto);

	GetItemDto readItem(Long itemId);

	GetItemDto updateItemInfo(Long itemId, UpdateItemDto requestDto);

	void updateItemStock(Long itemId, UpdateItemStockDto requestDto);

	void deleteItem(Long itemId);

}
