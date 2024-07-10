package vukv.bordercrossingbe.domain.dtos.border;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.NumberTemplate;
import lombok.*;
import vukv.bordercrossingbe.domain.PageableFilter;
import vukv.bordercrossingbe.domain.entities.border.Country;
import vukv.bordercrossingbe.domain.entities.border.QBorder;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BorderFilterRequest extends PageableFilter {

    private Country countryFrom;
    private Country countryTo;
    private String name;
    private Integer distance; //distance from the border in km
    private Double userLongitude;
    private Double userLatitude;

    @JsonIgnore
    @Builder.Default
    QBorder qBorder = QBorder.border;

    @JsonIgnore
    public BooleanBuilder getPredicate() {
        BooleanBuilder predicate = new BooleanBuilder();

        if (countryFrom != null) {
            predicate.and(qBorder.countryFrom.eq(countryFrom));
        }
        if (countryTo != null) {
            predicate.and(qBorder.countryTo.eq(countryTo));
        }
        if (name != null) {
            predicate.and(qBorder.name.containsIgnoreCase(name));
        }
        if (distance != null && userLatitude != null && userLongitude != null) {
            predicate.and(isWithinDistance(userLatitude, userLongitude, distance));
        }

        return predicate;
    }

    private BooleanExpression isWithinDistance(double userLatitude, double userLongitude, int distance) {
        // Check each point
        BooleanExpression entryStartCondition = haversineCondition(
                qBorder.location.entryStartLatitude,
                qBorder.location.entryStartLongitude,
                userLatitude,
                userLongitude,
                distance);
        BooleanExpression entryEndCondition = haversineCondition(
                qBorder.location.entryEndLatitude,
                qBorder.location.entryStartLongitude,
                userLatitude,
                userLongitude,
                distance);
        BooleanExpression exitStartCondition = haversineCondition(
                qBorder.location.exitStartLatitude,
                qBorder.location.entryStartLongitude,
                userLatitude,
                userLongitude,
                distance);
        BooleanExpression exitEndCondition = haversineCondition(
                qBorder.location.exitEndLatitude,
                qBorder.location.entryEndLongitude,
                userLatitude,
                userLongitude,
                distance);

        return entryStartCondition.or(entryEndCondition).or(exitStartCondition).or(exitEndCondition);
    }

    // Haversine implementation for QueryDSL
    public BooleanExpression haversineCondition(NumberPath<Double> borderLatitude, NumberPath<Double> borderLongitude, double userLatitude, double userLongitude, double expectedDistance) {
        // Distance between latitudes and longitudes
        NumberTemplate<Double> distanceLatitude = Expressions.numberTemplate(Double.class, "radians({0} - {1})", userLatitude, borderLatitude);
        NumberTemplate<Double> distanceLongitude = Expressions.numberTemplate(Double.class, "radians({0} - {1})", userLongitude, borderLongitude);

        // Convert to radians
        NumberTemplate<Double> userLatitudeRadians = Expressions.numberTemplate(Double.class, "radians({0})", userLatitude);
        NumberTemplate<Double> borderLatitudeRadians = Expressions.numberTemplate(Double.class, "radians({0})", borderLatitude);

        // Apply haversine
        NumberTemplate<Double> haversine = Expressions.numberTemplate(Double.class,
                "pow(sin({0} / 2), 2) + cos({1}) * cos({2}) * pow(sin({3} / 2), 2)",
                distanceLatitude, userLatitudeRadians, borderLatitudeRadians, distanceLongitude);

        // Calculate the distance
        double earthRadius = 6371;
        NumberTemplate<Double> angularDistanceRadians = Expressions.numberTemplate(Double.class, "2 * asin(sqrt({0}))", haversine);
        NumberTemplate<Double> distance = Expressions.numberTemplate(Double.class, "{0} * {1}", earthRadius, angularDistanceRadians);

        return distance.loe(expectedDistance);
    }

}
