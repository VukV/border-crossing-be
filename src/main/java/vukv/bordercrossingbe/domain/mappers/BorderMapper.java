package vukv.bordercrossingbe.domain.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import vukv.bordercrossingbe.domain.dtos.border.BorderCreateRequest;
import vukv.bordercrossingbe.domain.dtos.border.BorderDto;
import vukv.bordercrossingbe.domain.dtos.border.BorderUpdateRequest;
import vukv.bordercrossingbe.domain.entities.border.Border;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface BorderMapper {

    BorderMapper INSTANCE = Mappers.getMapper(BorderMapper.class);

    BorderDto toDto(Border border);

    Border fromCreateRequest(BorderCreateRequest request);

    void fromUpdateRequest(BorderUpdateRequest request, @MappingTarget Border border);

}
