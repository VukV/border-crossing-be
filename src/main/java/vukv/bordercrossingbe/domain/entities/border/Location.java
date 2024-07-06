package vukv.bordercrossingbe.domain.entities.border;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Location {

    // Entry line
    private double entryStartX;
    private double entryStartY;
    private double entryEndX;
    private double entryEndY;

    // Exit line
    private double exitStartX;
    private double exitStartY;
    private double exitEndX;
    private double exitEndY;

}
