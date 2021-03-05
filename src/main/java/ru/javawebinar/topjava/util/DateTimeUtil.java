package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static <T> boolean isBetweenHalfOpen(T lt, T startTime, T endTime) {
        if (startTime.getClass().getSimpleName().equals("LocalTime")) {
            LocalTime startT = (LocalTime) startTime;
            LocalTime endT = (LocalTime) endTime;
            LocalTime curT = (LocalTime) lt;
            return curT.compareTo(startT) >= 0 && curT.compareTo(endT) < 0;

        } else if (startTime.getClass().getSimpleName().equals("LocalDate")) {
            LocalDate startT = (LocalDate) startTime;
            LocalDate endT = (LocalDate) endTime;
            LocalDate curT = (LocalDate) lt;
            return curT.compareTo(startT) >= 0 && curT.compareTo(endT) <= 0;
        } else {
            return false;
        }
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
}

