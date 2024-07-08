package vukv.bordercrossingbe.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import vukv.bordercrossingbe.domain.dtos.border.BorderCreateRequest;
import vukv.bordercrossingbe.domain.dtos.border.BorderDto;
import vukv.bordercrossingbe.domain.dtos.border.BorderFilterRequest;
import vukv.bordercrossingbe.domain.dtos.border.BorderUpdateRequest;
import vukv.bordercrossingbe.domain.entities.border.Border;
import vukv.bordercrossingbe.domain.mappers.BorderMapper;
import vukv.bordercrossingbe.exception.exceptions.NotFoundException;
import vukv.bordercrossingbe.repositories.BorderRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional
@RequiredArgsConstructor
public class BorderService {

    private final BorderRepository borderRepository;

    public BorderDto getBorderById(UUID id) {
        Border border = borderRepository.findById(id).orElseThrow(() -> new NotFoundException("Border not found"));
        return BorderMapper.INSTANCE.toDto(border);
    }

    public Page<BorderDto> getAllBordersPageable(BorderFilterRequest borderFilterRequest) {
        return borderRepository.findAll(
                        borderFilterRequest.getPredicate(),
                        borderFilterRequest.getPageable())
                .map(BorderMapper.INSTANCE::toDto);

    }

    public List<BorderDto> getAllBorders(BorderFilterRequest borderFilterRequest) {
        Iterable<Border> borders = borderRepository.findAll(borderFilterRequest.getPredicate());
        return StreamSupport.stream(borders.spliterator(), false)
                .map(BorderMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    public BorderDto createBorder(BorderCreateRequest borderCreateRequest) {
        Border border = BorderMapper.INSTANCE.fromCreateRequest(borderCreateRequest);
        return BorderMapper.INSTANCE.toDto(borderRepository.save(border));
    }

    public BorderDto updateBorder(UUID id, BorderUpdateRequest borderUpdateRequest) {
        Border border = borderRepository.findById(id).orElseThrow(() -> new NotFoundException("Border not found"));
        BorderMapper.INSTANCE.fromUpdateRequest(borderUpdateRequest, border);
        return BorderMapper.INSTANCE.toDto(borderRepository.save(border));
    }

    public void deleteBorder(UUID id) {
        borderRepository.deleteById(id);
    }

}
