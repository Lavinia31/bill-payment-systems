package ro.orange.billing.billinginvoicecore.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
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
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    @EqualsAndHashCode.Exclude
    private Long paymentId;

    @Valid
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "invoice_id")
    //@OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    @ToString.Exclude
    private Invoice invoice;

    @Column(name = "payment_date")
    @NotNull
    private LocalDateTime paymentDate;

    @Column(name = "card_digits")
    @NotNull
    @Size(min = 4, max = 4)
    private String cardDigits;

    @Column(name = "status_code", length = 5)
    @NotNull
    private String statusCode;

    @Column(name = "status_message", length = 50)
    @NotNull
    private String statusMessage;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime insertedDate;

    @UpdateTimestamp
    private LocalDateTime updatedDate;
}

