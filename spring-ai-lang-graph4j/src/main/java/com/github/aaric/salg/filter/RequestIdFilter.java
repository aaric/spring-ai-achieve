package com.github.aaric.salg.filter;

import org.slf4j.MDC;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

import java.util.UUID;

/**
 * RequestIdFilter
 *
 * @author Aaric
 * @version 0.20.0-SNAPSHOT
 */
@Deprecated
//@Component
public class RequestIdFilter implements WebFilter {

    public static final String REQUEST_ID_HEADER_KEY = "X-Request-ID";
    public static final String REQUEST_ID_MDC_KEY = "requestId";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String requestId = exchange.getRequest().getHeaders().getFirst(REQUEST_ID_HEADER_KEY);
        if (requestId == null || requestId.isEmpty()) {
            requestId = UUID.randomUUID().toString();
        }

        exchange.getResponse().getHeaders().set(REQUEST_ID_HEADER_KEY, requestId);

        final String finalRequestId = requestId;
        return chain.filter(exchange)
                .contextWrite(Context.of(REQUEST_ID_MDC_KEY, requestId))
                .doOnEach(signal -> {
                    if (signal.isOnNext()) {
                        MDC.put(REQUEST_ID_MDC_KEY, finalRequestId);
                    }
                })
                .doFinally(signalType -> MDC.remove(REQUEST_ID_MDC_KEY));
    }
}
