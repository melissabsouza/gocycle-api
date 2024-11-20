package fiap.tds.gocycleapi.service.mapper;


import fiap.tds.gocycleapi.dto.PaymentDTO;
import fiap.tds.gocycleapi.model.Payment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    Payment toEntity(PaymentDTO paymentDTO);
    PaymentDTO toDto(Payment payment);
}
