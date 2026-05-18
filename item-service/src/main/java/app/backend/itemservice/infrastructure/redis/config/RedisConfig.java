package app.backend.itemservice.infrastructure.redis.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

@Configuration
@EnableRedisRepositories
public class RedisConfig {

	private static final String REDISSON_HOST_PREFIX = "redis://%s:%d";

	@Value("${spring.data.redis.host:localhost}")
	private String host;

	@Value("${spring.data.redis.port:6379}")
	private int port;

	@Value("${spring.data.redis.password:}")
	private String password;

	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(host, port);
		config.setPassword(password);
		return new LettuceConnectionFactory(config);
	}

	@Bean
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory cf) {
		ObjectMapper mapper = new ObjectMapper()
			.registerModule(new ParameterNamesModule(JsonCreator.Mode.PROPERTIES))
			.registerModule(new Jdk8Module())
			.registerModule(new JavaTimeModule());

		RedisTemplate<String, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(cf);
		template.setKeySerializer(new StringRedisSerializer());
		template.setValueSerializer(new GenericJackson2JsonRedisSerializer(mapper));
		template.afterPropertiesSet();
		return template;
	}

	@Bean
	public RedissonClient redissonClient() {
		Config config = new Config();

		SingleServerConfig singleServerConfig = config.useSingleServer()
			.setAddress(REDISSON_HOST_PREFIX.formatted(host, port))
			.setConnectionPoolSize(100)
			.setSubscriptionConnectionPoolSize(100)
			.setIdleConnectionTimeout(10_000)
			.setRetryAttempts(3)
			.setRetryInterval(100);

		if (password != null && !password.isBlank()) {
			singleServerConfig.setPassword(password);
		}

		return Redisson.create(config);
	}

}