package vukv.bordercrossingbe.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vukv.bordercrossingbe.domain.dtos.bordercrossing.BorderCrossingAnalyticsDto;
import vukv.bordercrossingbe.domain.entities.bordercrossing.BorderCrossing;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Repository
public interface BorderCrossingRepository extends JpaRepository<BorderCrossing, UUID>, QuerydslPredicateExecutor<BorderCrossing> {

    @Query(value = "SELECT AVG(bc.duration / 60) " +
            "FROM border_crossing bc " +
            "WHERE bc.border_id = :borderId " +
            "AND bc.duration IS NOT NULL " +
            "AND bc.arrival_timestamp >= :startDate " +
            "AND bc.arrival_timestamp <= :endDate",
            nativeQuery = true)
    Double findAverageDuration(@Param("borderId") UUID borderId, @Param("startDate") Instant startDate, @Param("endDate") Instant endDate);

    @Query(value = "SELECT AVG(duration / 60) AS avg_duration_minutes " +
            "FROM border_crossing " +
            "WHERE border_id = :borderId " +
            "AND duration IS NOT NULL " +
            "AND arrival_timestamp >= (DATE_TRUNC('HOUR', TIMEZONE(:userTimeZone, NOW())) - INTERVAL '7 days') " +
            "AND arrival_timestamp <= (DATE_TRUNC('HOUR', TIMEZONE(:userTimeZone, NOW())) + INTERVAL '1 hours') " +
            "AND EXTRACT(HOUR FROM (arrival_timestamp AT TIME ZONE :userTimeZone)) = :hourOfDay",
            nativeQuery = true)
    Double findAverageDurationForHourInLastWeek(@Param("borderId") UUID borderId, @Param("userTimeZone") String userTimeZone, @Param("hourOfDay") int hourOfDay);

    @Query(value = "SELECT EXTRACT(HOUR FROM (arrival_timestamp AT TIME ZONE :userTimeZone)) AS hourOfDay, " +
            "ROUND(AVG(duration / 60)) AS averageDuration " +
            "FROM border_crossing " +
            "WHERE border_id = :borderId " +
            "AND duration IS NOT NULL " +
            "AND arrival_timestamp >= (DATE_TRUNC('HOUR', TIMEZONE(:userTimeZone, NOW())) - INTERVAL '12 hours') " +
            "AND arrival_timestamp <= DATE_TRUNC('HOUR', TIMEZONE(:userTimeZone, NOW())) " +
            "GROUP BY hourOfDay " +
            "ORDER BY hourOfDay",
            nativeQuery = true)
    List<BorderCrossingAnalyticsDto.AverageByHour> findAverageDurationByHourInLast12Hours(@Param("borderId") UUID borderId, @Param("userTimeZone") String userTimeZone);

    @Query("SELECT bc FROM BorderCrossing bc WHERE bc.arrivalTimestamp <= :cutoff AND bc.duration IS NULL AND bc.crossingTimestamp IS NULL")
    List<BorderCrossing> findAllOlderThanAndIncomplete(@Param("cutoff") Instant cutoff);

}
