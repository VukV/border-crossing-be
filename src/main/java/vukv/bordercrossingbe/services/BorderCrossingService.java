package vukv.bordercrossingbe.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vukv.bordercrossingbe.domain.dtos.bordercrossing.BorderCrossingCreateRequest;
import vukv.bordercrossingbe.domain.dtos.bordercrossing.BorderCrossingDto;
import vukv.bordercrossingbe.domain.entities.border.Border;
import vukv.bordercrossingbe.domain.entities.bordercrossing.BorderCrossing;
import vukv.bordercrossingbe.domain.mappers.BorderCrossingMapper;
import vukv.bordercrossingbe.domain.mappers.BorderMapper;
import vukv.bordercrossingbe.exception.exceptions.BadRequestException;
import vukv.bordercrossingbe.exception.exceptions.NotFoundException;
import vukv.bordercrossingbe.repositories.BorderCrossingRepository;
import vukv.bordercrossingbe.repositories.BorderRepository;
import vukv.bordercrossingbe.utils.AuthUtils;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class BorderCrossingService {

    private final BorderCrossingRepository borderCrossingRepository;
    private final BorderRepository borderRepository;

    public List<BorderCrossingDto> getRecentCrossingsByBorderId(UUID borderId) {
        return borderCrossingRepository.findTop20ByBorder_IdOrderByCrossingTimestampDesc(borderId)
                .stream()
                .map(BorderCrossingMapper.INSTANCE::toDto)
                .toList();
    }

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

        if (borderCrossing.getArrivalTimestamp().isAfter(borderCrossing.getCrossingTimestamp())) {
            throw new BadRequestException("Arrival time is can't be before crossing time");
        }

        borderCrossing.setDuration();
        return BorderCrossingMapper.INSTANCE.toDto(borderCrossingRepository.save(borderCrossing));
    }

    public BorderCrossingDto crossedBorderManual(UUID borderId, BorderCrossingCreateRequest request) {
        if (request.getArrivalTimestamp().isAfter(request.getCrossingTimestamp())) {
            throw new BadRequestException("Arrival time is can't be before crossing time");
        }

        Border border = borderRepository.findById(borderId).orElseThrow(() -> new NotFoundException("Border not found"));
        BorderCrossing borderCrossing = BorderCrossing.builder()
                .border(border)
                .arrivalTimestamp(request.getArrivalTimestamp())
                .crossingTimestamp(request.getCrossingTimestamp())
                .createdBy(AuthUtils.getLoggedUser())
                .build();

        borderCrossing.setDuration();
        return BorderCrossingMapper.INSTANCE.toDto(borderCrossingRepository.save(borderCrossing));
    }

    public void deleteIncompleteCrossings() {
        Instant cutoff = Instant.now().minus(2, ChronoUnit.DAYS);
        List<BorderCrossing> incompleteBorderCrossings = borderCrossingRepository.findAllOlderThanAndIncomplete(cutoff);
        borderCrossingRepository.deleteAll(incompleteBorderCrossings);
    }

}
