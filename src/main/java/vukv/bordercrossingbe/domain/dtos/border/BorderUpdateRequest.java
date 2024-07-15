package vukv.bordercrossingbe.domain.dtos.border;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vukv.bordercrossingbe.domain.entities.border.Country;
import vukv.bordercrossingbe.domain.entities.border.Location;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BorderUpdateRequest {

    @NotEmpty(message = "Border name not defined")
    private String name;
    @NotEmpty(message = "Origin country not defined")
    private Country countryFrom;
    @NotEmpty(message = "Destination country not defined")
    private Country countryTo;
    @NotNull(message = "Border zone not defined")
    private Location location;

}
