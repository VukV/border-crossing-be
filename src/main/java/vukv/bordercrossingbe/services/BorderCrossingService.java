package vukv.bordercrossingbe.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vukv.bordercrossingbe.domain.dtos.bordercrossing.BorderCrossingDto;
import vukv.bordercrossingbe.domain.entities.border.Border;
import vukv.bordercrossingbe.domain.entities.bordercrossing.BorderCrossing;
import vukv.bordercrossingbe.domain.mappers.BorderCrossingMapper;
import vukv.bordercrossingbe.exception.exceptions.NotFoundException;
import vukv.bordercrossingbe.repositories.BorderCrossingRepository;
import vukv.bordercrossingbe.repositories.BorderRepository;
import vukv.bordercrossingbe.utils.AuthUtils;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class BorderCrossingService {

    private final BorderCrossingRepository borderCrossingRepository;
    private final BorderRepository borderRepository;

    public BorderCrossingDto arrivedAtBorder(UUID borderId) {
        Border border = borderRepository.findById(borderId).orElseThrow(() -> new NotFoundException("Border not found"));
        BorderCrossing borderCrossing = BorderCrossing.builder()
                .border(border)
                .arrivalTimestamp(Instant.now())
                .createdBy(AuthUtils.getLoggedUser())
                .build();

        return BorderCrossingMapper.INSTANCE.toDto(borderCrossingRepository.save(borderCrossing));
    }

    public BorderCrossingDto crossedBorder(UUID id) {
        BorderCrossing borderCrossing = borderCrossingRepository.findById(id).orElseThrow(() -> new NotFoundException("Crossing event doesn't exist"));
        borderCrossing.setCrossingTimestamp(Instant.now());
        borderCrossing.setDuration(Duration.between(borderCrossing.getArrivalTimestamp(), borderCrossing.getCrossingTimestamp()));
        return BorderCrossingMapper.INSTANCE.toDto(borderCrossingRepository.save(borderCrossing));
    }

}
