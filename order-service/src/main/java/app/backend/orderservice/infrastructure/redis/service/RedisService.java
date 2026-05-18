package app.backend.orderservice.infrastructure.redis.service;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RedisService {

	private final RedisTemplate<String, Object> redisTemplate;

	private final ObjectMapper objectMapper;

	public void set(final String key, final Object value, final Long time, final TimeUnit timeUnit) {
		redisTemplate.opsForValue().set(key, value, time, timeUnit);
	}

	public <T> T get(final String key, final Class<T> clazz) {

		Object value = redisTemplate.opsForValue().get(key);

		if (value == null) {
			return null;
		}

		return objectMapper.convertValue(value, clazz);
	}

	public boolean exists(final String key) {
		return redisTemplate.hasKey(key);
	}

	public void remove(final String key) {
		redisTemplate.delete(key);
	}

}
