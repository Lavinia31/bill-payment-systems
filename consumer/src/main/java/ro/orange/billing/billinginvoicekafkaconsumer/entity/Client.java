package ro.orange.billing.billinginvoicekafkaconsumer.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;


@Validated
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "client")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id")
    @EqualsAndHashCode.Exclude
    private Long clientId;

    @Valid
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "payment_method_id", referencedColumnName = "payment_method_id")
    @NotNull
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private PaymentMethod paymentMethod;

    @Column(name = "last_name", length = 50)
    @NotNull
    private String lastName;

    @Column(name = "first_name", length = 50)
    @NotNull
    private String firstName;

    @Column(name = "address", length = 50)
    @NotNull
    private String address;

    @Column(name = "phone_number")
    @NotNull
    @Size(min = 10, max = 10)
    private String phoneNumber;

    @Column(name = "email", length = 50)
    @NotNull
    private String email;

    @Column(name = "type", length = 50, nullable = false)
    @NotNull
    @Enumerated(EnumType.STRING)
    private Type type;

    @Column(name = "balance")
    @NotNull
    private BigDecimal balance;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime insertedDate;

    @UpdateTimestamp
    private LocalDateTime updatedDate;


    @JsonIgnore
    @OneToMany(mappedBy = "client")
    Set<Invoice> invoices;

    @Getter
    public enum Type {

        PRIVATE("private"),
        BUSINESS("business");

        private final String value;

        Type(String value) {
            this.value = value;
        }

        @JsonCreator
        public static Type fromValue(String text) {
            for (Type b : Type.values()) {
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

