package vukv.bordercrossingbe.domain.dtos.border;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vukv.bordercrossingbe.domain.entities.border.Country;
import vukv.bordercrossingbe.domain.entities.border.Location;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BorderDto {

    private UUID id;
    private String name;
    private Country countryFrom;
    private Country countryTo;
    private Location location;

}
