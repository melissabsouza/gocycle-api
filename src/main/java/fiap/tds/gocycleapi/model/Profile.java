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

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id_fk", referencedColumnName = "user_id", nullable = false)
    private User user;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "telephone_id_fk", referencedColumnName = "telephone_id", nullable = false)
    private Telephone telephone;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "address_id_fk", referencedColumnName = "address_id", nullable = false)
    private Address address;



}
