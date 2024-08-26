package vukv.bordercrossingbe.domain.entities.bordercrossing;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vukv.bordercrossingbe.domain.converters.DurationConverter;
import vukv.bordercrossingbe.domain.entities.border.Border;
import vukv.bordercrossingbe.domain.entities.user.User;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "border_crossing",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"id", "arrival_timestamp"})}
)
@IdClass(BorderCrossingId.class)
public class BorderCrossing {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Id
    @Column(nullable = false)
    private Instant arrivalTimestamp;

    private Instant crossingTimestamp;

    @Convert(converter = DurationConverter.class)
    private Duration duration;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Border border;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User createdBy;

    public void setCrossingTimestamp(Instant crossingTimestamp) {
        if (this.crossingTimestamp == null) {
            this.crossingTimestamp = crossingTimestamp;
        } else {
            throw new IllegalStateException("Crossing timestamp can only be set once.");
        }
    }

    public void setDuration() {
        if (this.duration == null) {
            this.duration = Duration.between(arrivalTimestamp, crossingTimestamp);
        } else {
            throw new IllegalStateException("Duration can only be set once.");
        }
    }

}
