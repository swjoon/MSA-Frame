package app.backend.gatewayserver.global.filter;

import java.util.UUID;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import app.backend.gatewayserver.global.constant.TraceConstants;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class TraceIdGlobalFilter implements GlobalFilter, Ordered {
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		String traceId = exchange.getRequest()
			.getHeaders()
			.getFirst(TraceConstants.TRACE_ID_HEADER);

		if (traceId == null || traceId.isBlank()) {
			traceId = UUID.randomUUID().toString();
		}

		String finalTraceId = traceId;

		ServerHttpRequest mutatedRequest = exchange.getRequest()
			.mutate()
			.header(TraceConstants.TRACE_ID_HEADER, finalTraceId)
			.build();

		ServerWebExchange mutatedExchange = exchange.mutate()
			.request(mutatedRequest)
			.build();

		log.info(
			"Gateway request started. traceId={}, method={}, path={}",
			finalTraceId,
			exchange.getRequest().getMethod(),
			exchange.getRequest().getURI().getPath()
		);

		return chain.filter(mutatedExchange)
			.doOnSuccess(unused -> {
				log.info(
					"Gateway request completed. traceId={}, status={}",
					finalTraceId,
					mutatedExchange.getResponse().getStatusCode()
				);
			})
			.doOnError(error -> {
				log.error(
					"Gateway request failed. traceId={}, message={}",
					finalTraceId,
					error.getMessage(),
					error
				);
			});
	}

	@Override
	public int getOrder() {
		return Ordered.HIGHEST_PRECEDENCE;
	}
}
