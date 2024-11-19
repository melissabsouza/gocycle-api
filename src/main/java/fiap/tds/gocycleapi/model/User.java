package fiap.tds.gocycleapi.model;

import fiap.tds.gocycleapi.model.enums.StatusType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.UniqueElements;

@Data
@Entity
@Table(name = "T_GS_USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @NotNull
    @Column(name = "user_email", unique=true, length = 100, nullable = false)
    private String email;

    @NotNull
    @Column(name = "user_password", length = 100, nullable = false)
    private String password;

    @NotNull
    @Column(name = "user_status", length = 100, nullable = false)
    private StatusType status = StatusType.ACTIVE;
}
