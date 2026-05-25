package org.parkingticketservice.utils;

import java.time.Duration;
import java.time.Instant;

public class TimeUtils {
    public static Instant calculateValidUntil(Instant createdAt) {
        return createdAt.plusSeconds(Duration.ofHours(2).toSeconds());
    }
}
