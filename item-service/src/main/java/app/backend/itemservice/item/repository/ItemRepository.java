package app.backend.itemservice.item.repository;

import java.util.Optional;

import app.backend.itemservice.item.entity.Item;

public interface ItemRepository {

	Item saveItem(Item item);

	Optional<Item> findItemById(Long itemId);

	void deleteItemById(Long itemId);

}
