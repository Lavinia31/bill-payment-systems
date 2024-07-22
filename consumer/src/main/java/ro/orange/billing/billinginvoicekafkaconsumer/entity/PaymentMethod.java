package ro.orange.billing.billinginvoicekafkaconsumer.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;

@Schema
@Validated
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "payment_method")
public class PaymentMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_method_id")
    @EqualsAndHashCode.Exclude
    private Long paymentMethodId;

    @Column(name = "card_digits")
    @NotNull
    @Size(min = 4, max = 4)
    private String cardDigits;

    @Column(name = "expiration_date")
    @NotNull
    @Size(min = 5, max = 5)
    @Check(constraints = "expiration_date ~ '^../..$'")
    private String expirationDate;

    @Column(name = "token", length = 50)
    @NotNull
    private String token;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime insertedDate;


    @UpdateTimestamp
    private LocalDateTime updatedDate;

    @JsonIgnore
    @OneToOne(mappedBy = "paymentMethod")
    private Client client;
}

