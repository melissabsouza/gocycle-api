package fiap.tds.gocycleapi.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "T_GS_ADDRESS")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long id;
    @Column(name = "address_street", length = 10, nullable = false)
    private String street;

    @Column(name = "address_number")
    private int number;

    @Column(name = "address_city", length = 10, nullable = false)
    private String city;

    @Column(name = "address_state", length = 10, nullable = false)
    private String state;

    @Column(name = "address_zipcode", length = 10, nullable = false)
    private String zipCode;

    @Column(name = "address_add_info", length = 10, nullable = false)
    private String addInfo;

    @OneToOne
    @JoinColumn(name = "profile_cpf_fk", referencedColumnName = "profile_cpf", nullable = false)
    private Profile profile;
}
