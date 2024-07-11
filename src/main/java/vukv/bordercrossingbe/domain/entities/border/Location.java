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
public class Location { // x is longitude, y is latitude

    private double longitude;
    private double latitude;

    // Entry line
    private double entryStartLongitude;
    private double entryStartLatitude;

    private double entryEndLongitude;
    private double entryEndLatitude;


    // Exit line
    private double exitStartLongitude;
    private double exitStartLatitude;

    private double exitEndLongitude;
    private double exitEndLatitude;

}
