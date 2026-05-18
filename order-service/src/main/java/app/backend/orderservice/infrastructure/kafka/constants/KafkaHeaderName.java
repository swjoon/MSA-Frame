package app.backend.orderservice.infrastructure.kafka.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class KafkaHeaderName {

	public static final String EVENT_ID = "id";
	public static final String EVENT_TYPE = "eventType";

}
