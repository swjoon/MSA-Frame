package app.backend.gatewayserver.global.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class L1Filter extends AbstractGatewayFilterFactory<L1Filter.Config> {

	public L1Filter() {
		super(Config.class);
	}

	@Override
	public GatewayFilter apply(Config config) {

		return (exchange, chain) -> {

			if (config.isPre()) {
				log.info("L1Filter before run");
			}

			return chain.filter(exchange)
				.then(Mono.fromRunnable(() -> {
					if (config.isPost()) {
						log.info("L1Filter after run");
					}
				}));
		};
	}

	@NoArgsConstructor
	@AllArgsConstructor
	@Data
	public static class Config {

		private boolean isPre;
		private boolean isPost;
	}
}
