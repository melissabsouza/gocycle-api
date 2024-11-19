package fiap.tds.gocycleapi.service.mapper;

import fiap.tds.gocycleapi.dto.AddressDTO;
import fiap.tds.gocycleapi.model.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper{
    Address toEntity(AddressDTO addressDTO);
    AddressDTO toDto(Address address);
}
