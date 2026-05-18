package app.backend.orderservice.infrastructure.kafka.message.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.backend.orderservice.infrastructure.kafka.message.entity.ProcessedMessage;
import app.backend.orderservice.infrastructure.kafka.message.repository.ProcessedMessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProcessedMessageServiceImpl implements ProcessedMessageService {

	private final ProcessedMessageRepository processedMessageRepository;

	@Override
	public boolean isProcessed(final String eventId, final String consumerName) {
		return processedMessageRepository.existsByEventIdAndConsumerName(eventId, consumerName);
	}

	@Override
	@Transactional
	public boolean markAsProcessed(
		final String eventId,
		final String consumerName,
		final String eventType,
		final String topicName,
		final Integer partitionNo,
		final Long offsetNo
	) {

		try {
			ProcessedMessage processedMessage = ProcessedMessage.create(
				eventId,
				consumerName,
				eventType,
				topicName,
				partitionNo,
				offsetNo
			);

			processedMessageRepository.createProcessedMessage(processedMessage);

			return true;
		} catch (DataIntegrityViolationException e) {

			log.info(
				"이미 소비된 event 입니다. eventId={}, consumerName={}, topicName={}, partitionNo={}, offsetNo={}",
				eventId,
				consumerName,
				topicName,
				partitionNo,
				offsetNo
			);

			return false;
		}
	}
}
