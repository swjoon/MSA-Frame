package app.backend.itemservice.infrastructure.kafka.outbox.service;

import org.slf4j.MDC;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import app.backend.itemservice.global.constant.TraceConstants;
import app.backend.itemservice.infrastructure.kafka.outbox.entity.OutboxEvent;
import app.backend.itemservice.infrastructure.kafka.outbox.exception.OutboxEventErrorCode;
import app.backend.itemservice.infrastructure.kafka.outbox.exception.OutboxEventException;
import app.backend.itemservice.infrastructure.kafka.outbox.repository.OutboxEventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class OutboxEventServiceImpl implements OutboxEventService {

	private final OutboxEventRepository outboxEventRepository;
	private final ObjectMapper objectMapper;

	public void publish(
		String aggregateType,
		String aggregateId,
		String eventType,
		String eventTopic,
		Object payload
	) {

		String traceId = MDC.get(TraceConstants.TRACE_ID_MDC_KEY);

		try {
			String jsonPayload = objectMapper.writeValueAsString(payload);

			OutboxEvent event = OutboxEvent.create(
				aggregateType,
				aggregateId,
				eventType,
				eventTopic,
				jsonPayload,
				traceId
			);

			outboxEventRepository.saveOutboxEvent(event);
		} catch (JsonProcessingException e) {

			log.info("Json processing 오류 발생 : traceId={}, event_message={}", traceId, e.getMessage());

			throw new OutboxEventException(OutboxEventErrorCode.JSON_PROCESSING_ERROR);
		}
	}
}
