package app.backend.orderservice.infrastructure.client.error;

import java.util.function.Supplier;

import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import app.backend.orderservice.global.constant.TraceConstants;
import app.backend.orderservice.infrastructure.client.constants.InternalServiceType;
import feign.FeignException;
import feign.RetryableException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class InternalFeignCallExecutor {

	private final InternalServiceExceptionFactory exceptionFactory;

	public <T> T execute(
		InternalServiceType targetService,
		Supplier<T> supplier
	) {
		String traceId = currentTraceId();

		try {
			return supplier.get();

		} catch (InternalServiceException ex) {
			throw ex;

		} catch (RetryableException ex) {

			log.warn(
				"내부 서비스 네트워크 통신에 실패했습니다. targetService={}, message={}",
				targetService.getServiceName(),
				ex.getMessage()
			);

			throw exceptionFactory.create(
				targetService,
				"INTERNAL_SERVICE_NETWORK_ERROR",
				targetService.getServiceName() + "와 통신할 수 없습니다.",
				HttpStatus.SERVICE_UNAVAILABLE.value(),
				traceId
			);

		} catch (FeignException ex) {
			log.warn(
				"Feign 호출 중 예외가 발생했습니다. targetService={}, status={}, message={}",
				targetService.getServiceName(),
				ex.status(),
				ex.getMessage()
			);

			int status = ex.status() > 0
				? ex.status()
				: HttpStatus.BAD_GATEWAY.value();

			throw exceptionFactory.create(
				targetService,
				"INTERNAL_SERVICE_FEIGN_ERROR",
				targetService.getServiceName() + " 호출 중 오류가 발생했습니다.",
				status,
				traceId
			);
		}
	}

	private String currentTraceId() {
		String traceId = MDC.get(TraceConstants.TRACE_ID_MDC_KEY);

		if (traceId == null || traceId.isBlank()) {
			return TraceConstants.NO_TRACE;
		}

		return traceId;
	}
}