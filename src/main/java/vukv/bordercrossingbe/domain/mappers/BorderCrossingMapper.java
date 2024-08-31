package vukv.bordercrossingbe.domain.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import vukv.bordercrossingbe.domain.dtos.bordercrossing.BorderCrossingDto;
import vukv.bordercrossingbe.domain.entities.bordercrossing.BorderCrossing;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface BorderCrossingMapper {

    BorderCrossingMapper INSTANCE = Mappers.getMapper(BorderCrossingMapper.class);

    BorderCrossingDto toDto(BorderCrossing borderCrossing);

}
