package com.github.aaric.salg.util;

import org.slf4j.MDC;

import java.util.UUID;

/**
 * MDC请求ID工具类
 *
 * @author Aaric
 * @version 0.20.0-SNAPSHOT
 */
public class MDCRequestIdUtil {

    public static final String MDC_KEY_REQUEST_ID = "requestId";

    public static String getRequestId() {
        String requestId = MDC.get(MDC_KEY_REQUEST_ID);
        if (requestId == null) {
            requestId = UUID.randomUUID()
                    .toString()
                    .toUpperCase();
            MDC.put(MDC_KEY_REQUEST_ID, requestId);
        }
        return requestId;
    }

    public static String removeRequestId() {
        String requestId = getRequestId();
        MDC.remove(MDC_KEY_REQUEST_ID);
        return requestId;
    }
}
