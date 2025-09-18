package com.github.aaric.salg.util;

import org.slf4j.MDC;

import java.util.UUID;

/**
 * RequestIdUtil
 *
 * @author Aaric
 * @version 0.20.0-SNAPSHOT
 */
public class RequestIdUtil {

    public static final String MDC_KEY_REQUEST_ID = "requestId";

    public static String get() {
        String requestId = MDC.get(MDC_KEY_REQUEST_ID);
        if (requestId == null) {
            requestId = UUID.randomUUID()
                    .toString()
                    .toUpperCase();
            MDC.put(MDC_KEY_REQUEST_ID, requestId);
        }
        return requestId;
    }

    public static String remove() {
        String requestId = get();
        MDC.remove(MDC_KEY_REQUEST_ID);
        return requestId;
    }
}
