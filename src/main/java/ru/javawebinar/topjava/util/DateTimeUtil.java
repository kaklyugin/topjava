package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static <T extends Comparable<T>> boolean isBetweenHalfOpen(T value, T lowerBound, T upperBound) {
        return value.compareTo(lowerBound) >= 0 && value.compareTo(upperBound) < 0;
    }

    public static <T extends Comparable<T>> boolean isBetweenIncludingBounds(T value, T lowerBound, T upperBound) {
        return value.compareTo(lowerBound) >= 0 && value.compareTo(upperBound) <= 0;
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }

    public static LocalDateTime calculateStartDateTime(LocalDate date, LocalTime time) {
        if (date == null)
            return LocalDateTime.MIN;
        else if (time == null)
            return date.atStartOfDay();
        return LocalDateTime.of(date, time);
    }

    public static LocalDateTime calculateEndDateTime(LocalDate date, LocalTime time) {
        if (date == null)
            return LocalDateTime.MAX;
        else if (time == null)
            return date.atStartOfDay().plusDays(1);
        return LocalDateTime.of(date, time);
    }
}

