package vukv.bordercrossingbe.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vukv.bordercrossingbe.domain.entities.border.Border;

import java.util.List;
import java.util.UUID;

@Repository
public interface BorderRepository extends JpaRepository<Border, UUID>, QuerydslPredicateExecutor<Border> {

    String HAVERSINE = "(6371 * acos(cos(radians(:latitude)) * cos(radians(b.location.latitude)) * cos(radians(b.location.longitude) - radians(:longitude)) + sin(radians(:latitude)) * sin(radians(b.location.latitude))))";

    @Query("SELECT b FROM Border b WHERE " + HAVERSINE + " < :distance")
    List<Border> findBordersWithinDistance(@Param("latitude") double userLatitude, @Param("longitude") double userLongitude, @Param("distance") double distance);

}
