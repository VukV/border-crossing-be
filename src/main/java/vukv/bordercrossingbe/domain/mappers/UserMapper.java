package vukv.bordercrossingbe.domain.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;
import vukv.bordercrossingbe.domain.dtos.auth.RegisterRequest;
import vukv.bordercrossingbe.domain.dtos.user.UserDto;
import vukv.bordercrossingbe.domain.entities.user.User;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto toDto(User user);

    User fromRegisterRequest(RegisterRequest request);

}
