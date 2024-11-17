package fiap.tds.gocycleapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {
    private Long id;
    private String street;
    private int number;
    private String city;
    private String state;
    private String zipCode;
    private String addInfo;
    private ProfileDTO profile;
}
