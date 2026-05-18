package app.backend.itemservice.infrastructure.kafka.event.handler;

import org.springframework.stereotype.Component;

import app.backend.itemservice.infrastructure.kafka.event.dto.KafkaEventMeta;
import app.backend.itemservice.infrastructure.kafka.event.dto.response.OrderItemReleasedEvent;
import app.backend.itemservice.infrastructure.kafka.message.service.ProcessedMessageService;
import app.backend.itemservice.item.dto.req.UpdateItemStockDto;
import app.backend.itemservice.item.entity.CalculationType;
import app.backend.itemservice.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ItemEventHandler {

	private final ItemService itemService;
	private final ProcessedMessageService processedMessageService;

	public void increaseItemStock(
		final OrderItemReleasedEvent event,
		final KafkaEventMeta meta,
		final String consumerName
	) {
		validateEventId(meta);

		boolean marked = processedMessageService.markAsProcessed(
			meta.eventId(),
			consumerName,
			meta.eventType(),
			meta.topicName(),
			meta.partitionNo(),
			meta.offsetNo()
		);

		if (!marked) {
			log.info(
				"이미 처리된 재고 복구 이벤트입니다. eventId={}, consumerName={}",
				meta.eventId(),
				consumerName
			);
			return;
		}

		itemService.updateItemStock(event.itemId(), UpdateItemStockDto.from(event.stock(), CalculationType.PLUS));

		log.info(
			"재고 복구 완료. itemId={}, stock={}",
			event.itemId(),
			event.stock()
		);
	}

	private void validateEventId(KafkaEventMeta meta) {
		if (meta.eventId() == null || meta.eventId().isBlank()) {
			throw new IllegalStateException("eventId header 가 없습니다.");
		}
	}
}
