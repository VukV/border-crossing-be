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

    private double longitude;
    private double latitude;

    // Entry
    private double entryLongitude;
    private double entryLatitude;

    // Exit
    private double exitLongitude;
    private double exitLatitude;

}
