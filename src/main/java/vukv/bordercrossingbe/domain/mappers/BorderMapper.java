package vukv.bordercrossingbe.domain.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import vukv.bordercrossingbe.domain.dtos.border.BorderDto;
import vukv.bordercrossingbe.domain.entities.border.Border;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface BorderMapper {

    BorderMapper INSTANCE = Mappers.getMapper(BorderMapper.class);

    BorderDto toDto(Border border);

}
