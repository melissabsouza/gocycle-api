package fiap.tds.gocycleapi.dto;

import fiap.tds.gocycleapi.model.enums.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {
    private Long id;
    private float amount;
    private PaymentType type;
    private UsageDTO usage;
}
