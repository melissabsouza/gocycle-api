package fiap.tds.gocycleapi.dto;


import fiap.tds.gocycleapi.model.Address;
import fiap.tds.gocycleapi.model.Telephone;
import fiap.tds.gocycleapi.model.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDTO {
    private String cpf;
    private Date birthdate;
    private String username;
    private int score;
    private String name;
    private UserDTO user;
    private TelephoneDTO telephone;
    private AddressDTO address;
}
