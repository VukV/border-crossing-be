package vukv.bordercrossingbe.domain.dtos.bordercrossing;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vukv.bordercrossingbe.domain.dtos.border.BorderDto;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BorderCrossingDto {

    private UUID id;
    private Instant arrivalTimestamp;
    private Instant crossingTimestamp;
    private Duration duration;
    private BorderDto border;
    private String createdBy;

}
