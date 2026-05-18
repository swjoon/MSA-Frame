package app.backend.orderservice.infrastructure.client.config;

import org.slf4j.MDC;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import app.backend.orderservice.global.constant.TraceConstants;
import feign.RequestInterceptor;

@Configuration
public class FeignTraceConfig {

	@Bean
	public RequestInterceptor traceIdRequestInterceptor() {
		return template -> {
			String traceId = MDC.get(TraceConstants.TRACE_ID_MDC_KEY);

			if (traceId != null && !traceId.isBlank()) {
				template.header(TraceConstants.TRACE_ID_HEADER, traceId);
			}
		};
	}
}
