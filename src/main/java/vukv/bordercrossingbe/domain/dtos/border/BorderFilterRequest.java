package vukv.bordercrossingbe.domain.dtos.border;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.querydsl.core.BooleanBuilder;
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
    private Double userLocationX;
    private Double userLocationY;

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
        if (distance != null && userLocationX != null && userLocationY != null) {
            // TODO calculate the distance from the border
        }

        return predicate;
    }

}
