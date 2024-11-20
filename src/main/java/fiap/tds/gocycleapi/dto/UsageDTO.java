package fiap.tds.gocycleapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsageDTO {
    private Long id;
    private ZonedDateTime pickupDateTime;
    private ZonedDateTime returnDateTime;
    private float duration;
    private int usageScore;
    private ProfileDTO profile;
    private PaymentDTO payment;
}
