package vukv.bordercrossingbe.domain.entities.bordercrossing;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BorderCrossingId implements Serializable {

    private UUID id;
    private Instant arrivalTimestamp;

}
