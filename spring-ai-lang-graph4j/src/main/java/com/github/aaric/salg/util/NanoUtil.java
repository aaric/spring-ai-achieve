package com.github.aaric.salg.util;

import java.time.Instant;

/**
 * NanoUtil
 *
 * @author Aaric
 * @version 0.20.0-SNAPSHOT
 */
public class NanoUtil {

    public static long getEpochSecondNano() {
        Instant current = Instant.now();
        return current.getEpochSecond() * 1_000_000_000L
                + current.getNano();
    }
}
