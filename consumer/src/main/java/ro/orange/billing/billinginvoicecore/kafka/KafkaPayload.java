package ro.orange.billing.billinginvoicecore.kafka;

import lombok.*;
import ro.orange.billing.billinginvoicekafkaconsumer.entity.Client;
import ro.orange.billing.billinginvoicekafkaconsumer.entity.Invoice;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class KafkaPayload {
    private Client client;
    private Invoice invoice;
}
