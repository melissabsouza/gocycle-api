package fiap.tds.gocycleapi.dto;


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
}
