package vukv.bordercrossingbe.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vukv.bordercrossingbe.domain.dtos.bordercrossing.BorderCrossingAnalyticsDto;
import vukv.bordercrossingbe.repositories.BorderCrossingRepository;

import java.time.*;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class BorderCrossingAnalyticsService {

    private final BorderCrossingRepository borderCrossingRepository;

    public BorderCrossingAnalyticsDto getAnalyticsByBorderId(UUID borderId, String userTimeZone) {
        BorderCrossingAnalyticsDto analytics = BorderCrossingAnalyticsDto.builder()
                .averageToday(getAverageToday(borderId, userTimeZone))
                .averageWeek(getAverageThisWeek(borderId, userTimeZone))
                .averageMonth(getAverageThisMonth(borderId, userTimeZone))
                .build();

        //TODO

        return null;
    }

    private long getAverageCurrentHour(UUID borderId, String userTimeZone) {
        // TODO
        return 0;
    }

    private long getAverageToday(UUID borderId, String userTimeZone) {
        Instant startOfDayInstant = LocalDate.now(ZoneId.of(userTimeZone)).atStartOfDay(ZoneId.of(userTimeZone)).toInstant();
        Instant endOfDayInstant = startOfDayInstant.plus(Duration.ofDays(1));

        Duration averageDuration = borderCrossingRepository.findAverageDuration(borderId, startOfDayInstant, endOfDayInstant);
        return averageDuration != null ? averageDuration.toMinutes() : 0;
    }

    private long getAverageThisWeek(UUID borderId, String userTimeZone) {
        LocalDate today = LocalDate.now(ZoneId.of(userTimeZone));
        LocalDate startOfWeek = today.with(DayOfWeek.MONDAY);
        Instant startOfWeekInstant = startOfWeek.atStartOfDay(ZoneId.of(userTimeZone)).toInstant();

        LocalDate endOfWeek = startOfWeek.plusDays(6);
        Instant endOfWeekInstant = endOfWeek.atTime(LocalTime.MAX).atZone(ZoneId.of(userTimeZone)).toInstant();

        Duration averageDuration = borderCrossingRepository.findAverageDuration(borderId, startOfWeekInstant, endOfWeekInstant);
        return averageDuration != null ? averageDuration.toMinutes() : 0;
    }

    private long getAverageThisMonth(UUID borderId, String userTimeZone) {
        LocalDate today = LocalDate.now(ZoneId.of(userTimeZone));
        LocalDate startOfMonth = today.withDayOfMonth(1);
        Instant startOfMonthInstant = startOfMonth.atStartOfDay(ZoneId.of(userTimeZone)).toInstant();

        LocalDate endOfMonth = today.withDayOfMonth(today.lengthOfMonth());
        Instant endOfMonthInstant = endOfMonth.atTime(LocalTime.MAX).atZone(ZoneId.of(userTimeZone)).toInstant();

        Duration averageDuration = borderCrossingRepository.findAverageDuration(borderId, startOfMonthInstant, endOfMonthInstant);
        return averageDuration != null ? averageDuration.toMinutes() : 0;
    }

}
