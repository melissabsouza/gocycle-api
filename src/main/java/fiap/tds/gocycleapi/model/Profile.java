package fiap.tds.gocycleapi.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "T_GS_PROFILE")
public class Profile {
    @Id
    @Column(name = "profile_cpf")
    private String cpf;

    @Column(name = "profile_birthdate")
    private Date birthdate;

    @Column(name = "profile_username", length = 100, nullable = false)
    private String username;

    @Column(name = "profile_score", length = 100, nullable = false)
    private int score;

    @Column(name = "profile_name", length = 100, nullable = false)
    private String name;

    @OneToOne
    @JoinColumn(name = "user_id_fk", referencedColumnName = "user_id", nullable = false)
    private User user;


}
