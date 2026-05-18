package app.backend.orderservice.global.filter;

import java.io.IOException;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import app.backend.orderservice.global.constant.TraceConstants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TraceIdFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(
		HttpServletRequest request,
		HttpServletResponse response,
		FilterChain filterChain
	) throws ServletException, IOException {

		String traceId = request.getHeader(TraceConstants.TRACE_ID_HEADER);

		if (traceId == null || traceId.isBlank()) {
			traceId = TraceConstants.NO_TRACE;
		}

		try {
			MDC.put(TraceConstants.TRACE_ID_MDC_KEY, traceId);

			response.setHeader(TraceConstants.TRACE_ID_HEADER, traceId);

			log.info("order service start job : traceId : {}", traceId);

			filterChain.doFilter(request, response);

		} finally {
			MDC.remove(TraceConstants.TRACE_ID_MDC_KEY);
		}
	}
}
