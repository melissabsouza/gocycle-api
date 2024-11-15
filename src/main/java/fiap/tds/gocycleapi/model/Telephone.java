package fiap.tds.gocycleapi.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "T_GS_TELEPHONE")
public class Telephone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "telephone_id")
    private Long id;

    @Column(name = "telephone_ddd", length = 10, nullable = false)
    private String ddd;

    @Column(name = "telephone_number", length = 25, nullable = false)
    private String number;

    @OneToOne
    @JoinColumn(name = "profile_cpf", referencedColumnName = "profile_cpf", nullable = false)
    private Profile profile;


}
