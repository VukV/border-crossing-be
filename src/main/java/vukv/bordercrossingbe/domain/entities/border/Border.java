package vukv.bordercrossingbe.domain.entities.border;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "border")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Border {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Country countryFrom;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Country countryTo;

    @Embedded
    @Column(nullable = false)
    private Location location;

}
