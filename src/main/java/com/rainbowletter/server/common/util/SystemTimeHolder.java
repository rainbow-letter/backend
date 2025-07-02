package com.rainbowletter.server.common.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class SystemTimeHolder implements TimeHolder {

    private static final long DAY_TO_HOUR = 24L;
    private static final long HOUR_TO_MINUTE = 60L;
    private static final long MINUTE_TO_SECOND = 60L;
    private static final long SECOND_TO_MILLISECOND = 1000L;

    @Override
    public LocalDateTime currentTime() {
        return LocalDateTime.now();
    }

    @Override
    public LocalDate currentDate() {
        return LocalDate.now();
    }

    @Override
    public long currentTimeMillis() {
        return System.currentTimeMillis();
    }

    @Override
    public long dayToMillis(final int day) {
        return day * DAY_TO_HOUR * HOUR_TO_MINUTE * MINUTE_TO_SECOND * SECOND_TO_MILLISECOND;
    }

    @Override
    public long minuteToMillis(final int minute) {
        return minute * MINUTE_TO_SECOND * SECOND_TO_MILLISECOND;
    }

}
