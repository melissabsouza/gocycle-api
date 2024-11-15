package fiap.tds.gocycleapi.model;

import fiap.tds.gocycleapi.model.enums.StatusType;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "T_GS_USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_email", length = 100, nullable = false)
    private String email;

    @Column(name = "user_password", length = 100, nullable = false)
    private String password;

    @Column(name = "user_status", length = 100, nullable = false)
    private StatusType status;
}
