package vukv.bordercrossingbe.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vukv.bordercrossingbe.domain.dtos.border.BorderDto;
import vukv.bordercrossingbe.domain.dtos.bordercrossing.BorderCrossingAnalyticsDto;
import vukv.bordercrossingbe.domain.entities.border.Border;
import vukv.bordercrossingbe.domain.mappers.BorderMapper;
import vukv.bordercrossingbe.exception.exceptions.NotFoundException;
import vukv.bordercrossingbe.repositories.BorderCrossingRepository;
import vukv.bordercrossingbe.repositories.BorderRepository;

import java.time.*;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class BorderCrossingAnalyticsService {

    private final BorderCrossingRepository borderCrossingRepository;
    private final BorderRepository borderRepository;

    public BorderCrossingAnalyticsDto getAnalyticsByBorderId(UUID borderId, String userTimeZone) {
        Border border = borderRepository.findById(borderId).orElseThrow(() -> new NotFoundException("Border not found"));
        BorderDto borderDto = BorderMapper.INSTANCE.toDto(border);

        return BorderCrossingAnalyticsDto.builder()
                .border(borderDto)
                .averageToday(getAverageToday(borderId, userTimeZone))
                .averageWeek(getAverageThisWeek(borderId, userTimeZone))
                .averageMonth(getAverageThisMonth(borderId, userTimeZone))
                .averageCurrentHour(getAverageCurrentHour(borderId, userTimeZone))
                .averageByHour(getAverageByHourLast12Hours(borderId, userTimeZone))
                .build();
    }

    private long getAverageCurrentHour(UUID borderId, String userTimeZone) {
        int currentHour = LocalDateTime.now(ZoneId.of(userTimeZone)).getHour();
        Double averageTime = borderCrossingRepository.findAverageDurationForHourInLastWeek(borderId, userTimeZone, currentHour);

        return averageTime != null ? averageTime.longValue() : 0L;
    }

    private long getAverageToday(UUID borderId, String userTimeZone) {
        Instant startOfDayInstant = LocalDate.now(ZoneId.of(userTimeZone)).atStartOfDay(ZoneId.of(userTimeZone)).toInstant();
        Instant endOfDayInstant = startOfDayInstant.plus(Duration.ofDays(1));

        Double averageDuration = borderCrossingRepository.findAverageDuration(borderId, startOfDayInstant, endOfDayInstant);
        return averageDuration != null ? averageDuration.longValue() : 0;
    }

    private long getAverageThisWeek(UUID borderId, String userTimeZone) {
        LocalDate today = LocalDate.now(ZoneId.of(userTimeZone));
        LocalDate startOfWeek = today.with(DayOfWeek.MONDAY);
        Instant startOfWeekInstant = startOfWeek.atStartOfDay(ZoneId.of(userTimeZone)).toInstant();

        LocalDate endOfWeek = startOfWeek.plusDays(6);
        Instant endOfWeekInstant = endOfWeek.atTime(LocalTime.MAX).atZone(ZoneId.of(userTimeZone)).toInstant();

        Double averageDuration = borderCrossingRepository.findAverageDuration(borderId, startOfWeekInstant, endOfWeekInstant);
        return averageDuration != null ? averageDuration.longValue() : 0;
    }

    private long getAverageThisMonth(UUID borderId, String userTimeZone) {
        LocalDate today = LocalDate.now(ZoneId.of(userTimeZone));
        LocalDate startOfMonth = today.withDayOfMonth(1);
        Instant startOfMonthInstant = startOfMonth.atStartOfDay(ZoneId.of(userTimeZone)).toInstant();

        LocalDate endOfMonth = today.withDayOfMonth(today.lengthOfMonth());
        Instant endOfMonthInstant = endOfMonth.atTime(LocalTime.MAX).atZone(ZoneId.of(userTimeZone)).toInstant();

        Double averageDuration = borderCrossingRepository.findAverageDuration(borderId, startOfMonthInstant, endOfMonthInstant);
        return averageDuration != null ? averageDuration.longValue() : 0;
    }

    private List<BorderCrossingAnalyticsDto.AverageByHour> getAverageByHourLast12Hours(UUID borderId, String userTimeZone) {
        return borderCrossingRepository.findAverageDurationByHourInLast12Hours(borderId, userTimeZone);
    }

}
