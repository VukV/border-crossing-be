package vukv.bordercrossingbe.domain.dtos.bordercrossing;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BorderCrossingCreateRequest {

    @NotNull(message = "Arrival time not defined")
    private Instant arrivalTimestamp;
    @NotNull(message = "Crossing time not defined")
    private Instant crossingTimestamp;

}
