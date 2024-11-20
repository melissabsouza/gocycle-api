package fiap.tds.gocycleapi.service.mapper;

import fiap.tds.gocycleapi.dto.UsageDTO;
import fiap.tds.gocycleapi.model.Usage;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsageMapper {
    Usage toEntity(UsageDTO usageDTO);
    UsageDTO toDto(Usage usage);
}
