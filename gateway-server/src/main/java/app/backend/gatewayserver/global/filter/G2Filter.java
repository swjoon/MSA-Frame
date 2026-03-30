package app.backend.gatewayserver.global.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class G2Filter implements GlobalFilter, Ordered {

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		log.info("G2Filter before run ");

		return chain.filter(exchange)
			.then(Mono.fromRunnable(() -> {

				log.info("G2Filter after run");
			}));
	}

	@Override
	public int getOrder() {

		return -2;
	}
}
