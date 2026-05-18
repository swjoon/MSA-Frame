package app.backend.orderservice.infrastructure.client.item.adapter;

import org.springframework.stereotype.Service;

import app.backend.orderservice.infrastructure.client.constants.InternalServiceType;
import app.backend.orderservice.infrastructure.client.error.InternalFeignCallExecutor;
import app.backend.orderservice.infrastructure.client.item.dto.req.GetItemDto;
import app.backend.orderservice.infrastructure.client.item.dto.res.UpdateItemStockDto;
import app.backend.orderservice.infrastructure.client.item.service.ItemServiceClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemAdapter {

	private final ItemServiceClient itemServiceClient;
	private final InternalFeignCallExecutor feignCallExecutor;

	public GetItemDto getItem(final Long itemId) {

		return feignCallExecutor.execute(
			InternalServiceType.ITEM,
			() -> itemServiceClient.getItemDto(itemId)
		);
	}

	public void updateItemStock(final Long itemId, final UpdateItemStockDto requestDto) {

		feignCallExecutor.execute(
			InternalServiceType.ITEM,
			() -> {
				itemServiceClient.updateItemStock(itemId, requestDto);
				return null;
			}
		);
	}

}
