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

//    @Query("SELECT b FROM Border b WHERE " +
//            "(6371 * 2 * atan2(sqrt(pow(sin(radians((:userLatitude - b.location.entryStartLatitude) / 2)), 2) + " +
//            "cos(radians(:userLatitude)) * cos(radians(b.location.entryStartLatitude)) * pow(sin(radians((:userLongitude - b.location.entryStartLongitude) / 2)), 2)), " +
//            "sqrt(1 - pow(sin(radians((:userLatitude - b.point1Latitude) / 2)), 2) + cos(radians(:userLatitude)) * cos(radians(b.point1Latitude)) * " +
//            "pow(sin(radians((:userLongitude - b.point1Longitude) / 2)), 2)))) <= :distance OR " +
//
//            "(6371 * 2 * atan2(sqrt(pow(sin(radians((:userLatitude - b.point2Latitude) / 2)), 2) + " +
//            "cos(radians(:userLatitude)) * cos(radians(b.point2Latitude)) * pow(sin(radians((:userLongitude - b.point2Longitude) / 2)), 2)), " +
//            "sqrt(1 - pow(sin(radians((:userLatitude - b.point2Latitude) / 2)), 2) + cos(radians(:userLatitude)) * cos(radians(b.point2Latitude)) * " +
//            "pow(sin(radians((:userLongitude - b.point2Longitude) / 2)), 2)))) <= :distance OR " +
//
//            "(6371 * 2 * atan2(sqrt(pow(sin(radians((:userLatitude - b.point3Latitude) / 2)), 2) + " +
//            "cos(radians(:userLatitude)) * cos(radians(b.point3Latitude)) * pow(sin(radians((:userLongitude - b.point3Longitude) / 2)), 2)), " +
//            "sqrt(1 - pow(sin(radians((:userLatitude - b.point3Latitude) / 2)), 2) + cos(radians(:userLatitude)) * cos(radians(b.point3Latitude)) * " +
//            "pow(sin(radians((:userLongitude - b.point3Longitude) / 2)), 2)))) <= :distance OR " +
//
//            "(6371 * 2 * atan2(sqrt(pow(sin(radians((:userLatitude - b.point4Latitude) / 2)), 2) + " +
//            "cos(radians(:userLatitude)) * cos(radians(b.point4Latitude)) * pow(sin(radians((:userLongitude - b.point4Longitude) / 2)), 2)), " +
//            "sqrt(1 - pow(sin(radians((:userLatitude - b.point4Latitude) / 2)), 2) + cos(radians(:userLatitude)) * cos(radians(b.point4Latitude)) * " +
//            "pow(sin(radians((:userLongitude - b.point4Longitude) / 2)), 2)))) <= :distance")
//    List<Border> findBordersWithinDistance(@Param("userLatitude") double userLatitude,
//                                           @Param("userLongitude") double userLongitude,
//                                           @Param("distance") double distance);

}
