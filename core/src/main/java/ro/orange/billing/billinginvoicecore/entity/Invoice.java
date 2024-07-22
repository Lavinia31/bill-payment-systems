package ro.orange.billing.billinginvoicecore.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

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
@Table(name = "invoice")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invoice_id")
    @EqualsAndHashCode.Exclude
    private Long invoiceId;

    @Valid
    @ManyToOne(cascade = CascadeType.ALL)
    @NotNull
    @JoinColumn(name = "client_id")
    //@OnDelete(action = OnDeleteAction.CASCADE)
    @ToString.Exclude
    private Client client;

    @Column(name = "invoice_type", length = 3)
    @NotNull
    @Enumerated(EnumType.STRING)
    private InvoiceType invoiceType;

    @Column(name = "issued_date")
    @NotNull
    private LocalDateTime issuedDate;

    @Column(name = "due_date")
    @NotNull
    private LocalDateTime dueDate;

    @Column(name = "amount")
    @NotNull
    @DecimalMin(value = "0.01")
    private BigDecimal amount;

    @Column(name = "services", columnDefinition = "TEXT")
    @NotNull
    private String services;

    @Column(name = "payment_status", length = 50)
    @NotNull
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime insertedDate;

    @UpdateTimestamp
    private LocalDateTime updatedDate;

    @JsonIgnore
    @OneToMany(mappedBy = "invoice",cascade = CascadeType.ALL, orphanRemoval = true)
    Set<Payment> payments;

    @Getter
    public enum InvoiceType {

        B2B("b2b"),
        B2C("b2c");

        private final String value;

        InvoiceType(String value) {
            this.value = value;
        }

        @JsonCreator
        public static Invoice.InvoiceType fromValue(String text) {
            for (Invoice.InvoiceType b : Invoice.InvoiceType.values()) {
                if (String.valueOf(b.value).equals(text)) {
                    return b;
                }
            }
            return null;
        }

        @Override
        @JsonValue
        public String toString() {
            return String.valueOf(value);
        }

    }

    @Getter
    public enum PaymentStatus {

        PENDING("pending"),
        SUCCESSFUL("successful"),
        FAILED("failed");

        private final String value;

        PaymentStatus(String value) {
            this.value = value;
        }

        @JsonCreator
        public static Invoice.PaymentStatus fromValue(String text) {
            for (Invoice.PaymentStatus b : Invoice.PaymentStatus.values()) {
                if (String.valueOf(b.value).equals(text)) {
                    return b;
                }
            }
            return null;
        }

        @Override
        @JsonValue
        public String toString() {
            return String.valueOf(value);
        }

    }
}

