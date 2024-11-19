package fiap.tds.gocycleapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TelephoneDTO {
    private Long id;
    private String ddd;
    private String number;
}
