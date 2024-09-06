package vukv.bordercrossingbe.domain.mappers;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import vukv.bordercrossingbe.domain.dtos.border.BorderCreateRequest;
import vukv.bordercrossingbe.domain.dtos.border.BorderDto;
import vukv.bordercrossingbe.domain.dtos.border.BorderUpdateRequest;
import vukv.bordercrossingbe.domain.entities.border.Border;
import vukv.bordercrossingbe.domain.entities.user.User;
import vukv.bordercrossingbe.utils.AuthUtils;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface BorderMapper {

    BorderMapper INSTANCE = Mappers.getMapper(BorderMapper.class);

    @Mapping(target = "favorite", source = "border", qualifiedByName = "isFavorite")
    BorderDto toDto(Border border);

    @Named("isFavorite")
    default boolean isFavorite(Border border) {
        User loggedUser = AuthUtils.getLoggedUser();
        return loggedUser.getFavoriteBorders().contains(border);
    }

    Border fromCreateRequest(BorderCreateRequest request);

    void fromUpdateRequest(BorderUpdateRequest request, @MappingTarget Border border);

}
