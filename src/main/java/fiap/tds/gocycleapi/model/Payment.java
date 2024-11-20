package fiap.tds.gocycleapi.model;

import fiap.tds.gocycleapi.model.enums.PaymentType;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "T_GS_PAYMENT")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long id;

    @Column(name = "payment_amount")
    private float amount;

    @Column(name = "payment_type", length = 20, nullable = false)
    private PaymentType type;
}
