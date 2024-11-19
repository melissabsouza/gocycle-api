package fiap.tds.gocycleapi.service.mapper;


import fiap.tds.gocycleapi.dto.TelephoneDTO;
import fiap.tds.gocycleapi.model.Telephone;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TelephoneMapper {
    Telephone toEntity(TelephoneDTO telephoneDTO);
    TelephoneDTO toDto(Telephone telephone);
}
