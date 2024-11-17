package fiap.tds.gocycleapi.service.mapper;

import fiap.tds.gocycleapi.dto.UserDTO;
import fiap.tds.gocycleapi.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserDTO userDTO);
    UserDTO toDto(User user);
}
