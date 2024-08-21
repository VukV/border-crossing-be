package vukv.bordercrossingbe.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vukv.bordercrossingbe.domain.entities.bordercrossing.BorderCrossing;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Repository
public interface BorderCrossingRepository extends JpaRepository<BorderCrossing, UUID>, QuerydslPredicateExecutor<BorderCrossing> {

    @Query("SELECT AVG(bc.duration) " +
            "FROM BorderCrossing bc " +
            "WHERE bc.border.id = :borderId " +
            "AND bc.duration IS NOT NULL " +
            "AND bc.arrivalTimestamp >= :startDate " +
            "AND bc.arrivalTimestamp < :endDate")
    Duration findAverageDuration(@Param("borderId") UUID borderId, @Param("startOfDay") Instant startDate, @Param("endOfDay") Instant endDate);

    @Query(value = "SELECT AVG(EXTRACT(EPOCH FROM duration) / 60) AS avg_duration_minutes " +
            "FROM border_crossing " +
            "WHERE border_id = :borderId " +
            "AND duration IS NOT NULL " +
            "AND arrival_timestamp >= (DATE_TRUNC('HOUR', TIMEZONE(:userTimeZone, NOW())) - INTERVAL '7 days') " +
            "AND arrival_timestamp < (DATE_TRUNC('HOUR', TIMEZONE(:userTimeZone, NOW()))) " +
            "AND EXTRACT(HOUR FROM (arrival_timestamp AT TIME ZONE :userTimeZone)) = :hourOfDay",
            nativeQuery = true)
    Double findAverageDurationForHourInLastWeek(@Param("borderId") UUID borderId, @Param("userTimeZone") String userTimeZone, @Param("hourOfDay") int hourOfDay);

}
