package fiap.tds.gocycleapi.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@Entity
@Table(name = "T_GS_USAGE")
public class Usage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usage_id")
    private Long id;

    @Column(name = "pickup_datetime")
    private ZonedDateTime pickupDateTime;

    @Column(name = "return_datetime")
    private ZonedDateTime returnDateTime;

    @Column(name = "usage_duration")
    private float duration;

    @Column(name = "usage_score")
    private int usageScore;

    @ManyToOne
    @JoinColumn(name = "profile_cpf_fk")
    private Profile profile;

    @OneToOne
    @JoinColumn(name = "payment_id_fk", referencedColumnName = "payment_id", nullable = false)
    private Payment payment;

}
