package com.minhkha.post.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Component
public class MyDateTimeFormatter {

    Map<Long, Function<Instant, String>> strategyMap = new LinkedHashMap<>( );

    public MyDateTimeFormatter() {
        strategyMap.put(60L, this::formatInSeconds);
        strategyMap.put(3600L, this::formatInMinutes);
        strategyMap.put(86400L, this::formatInHours);
        strategyMap.put(604800L, this::formatInDays);
        strategyMap.put(2592000L, this::formatInWeeks);
        strategyMap.put(31536000L, this::formatInMonths);
        strategyMap.put(Long.MAX_VALUE, this::formatInYears);
    }

    private String formatInSeconds(Instant instant) {
        long elapseSeconds = ChronoUnit.SECONDS.between(instant, Instant.now());
        return elapseSeconds + " seconds ago";
    }

    private String formatInMinutes(Instant instant) {
        long elapseMinutes = ChronoUnit.MINUTES.between(instant, Instant.now());
        return elapseMinutes + " minutes ago";
    }

    private String formatInHours(Instant instant) {
        long elapseHours = ChronoUnit.HOURS.between(instant, Instant.now());
        return elapseHours + " hours ago";
    }

    private String formatInDays(Instant instant) {
        long days = ChronoUnit.DAYS.between(instant, Instant.now());
        return days + " days ago";
    }

    private String formatInWeeks(Instant instant) {
        long weeks = ChronoUnit.WEEKS.between(instant, Instant.now());
        return weeks + " weeks ago";
    }

    private String formatInMonths(Instant instant) {
        long months = ChronoUnit.MONTHS.between(
                instant.atZone(ZoneId.systemDefault()).toLocalDate(),
                LocalDateTime.now().atZone(ZoneId.systemDefault()).toLocalDate()
        );
        return months + " months ago";
    }

    private String formatInYears(Instant instant) {
        long years = ChronoUnit.YEARS.between(
                instant.atZone(ZoneId.systemDefault()).toLocalDate(),
                LocalDateTime.now().atZone(ZoneId.systemDefault()).toLocalDate()
        );
        return years + " years ago";
    }

    public String format(Instant instant) {
        long elapseSeconds = ChronoUnit.SECONDS.between(instant, Instant.now());

        var strategy = strategyMap.entrySet()
                .stream()
                .filter(entry -> elapseSeconds < entry.getKey())
                .findFirst()
                .get();

        return strategy.getValue().apply(instant);
    }
}
