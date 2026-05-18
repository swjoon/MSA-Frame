package app.backend.orderservice.infrastructure.kafka.util;

import java.nio.charset.StandardCharsets;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Header;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class KafkaHeaderUtils {

	public static String getHeaderAsString(ConsumerRecord<String, String> record, String headerName) {
		Header header = record.headers().lastHeader(headerName);

		if (header == null || header.value() == null) {
			return null;
		}

		return new String(header.value(), StandardCharsets.UTF_8);
	}

}
