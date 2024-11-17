package fiap.tds.gocycleapi.service.mapper;

import fiap.tds.gocycleapi.dto.ProfileDTO;
import fiap.tds.gocycleapi.model.Profile;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    Profile toEntity(ProfileDTO profileDTO);
    ProfileDTO toDto(Profile profile);
}
