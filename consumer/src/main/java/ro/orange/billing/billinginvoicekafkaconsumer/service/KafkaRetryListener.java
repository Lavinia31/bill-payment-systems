package ro.orange.billing.billinginvoicekafkaconsumer.service;

import lombok.extern.slf4j.Slf4j;
        import org.apache.kafka.clients.consumer.ConsumerRecord;
        import org.springframework.beans.factory.annotation.Value;
        import org.springframework.kafka.listener.RetryListener;
        import org.springframework.lang.NonNull;
        import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaRetryListener implements RetryListener {

    @Value("${kafka.maxRetryAttempts}")
    private int maxRetryAttempts;

    @Override
    public void failedDelivery(@NonNull ConsumerRecord<?, ?> consumerRecord, @NonNull Exception ex, int deliveryAttempt) {

        if (log.isErrorEnabled()) {
            log.error(
                    "Error during consuming record. Attempt {} out of {} for consumer record key {} topic {}, partition {}, offset {}, timestamp {}.",
                    deliveryAttempt,
                    maxRetryAttempts,
                    consumerRecord.key(),
                    consumerRecord.topic(),
                    consumerRecord.partition(),
                    consumerRecord.offset(),
                    consumerRecord.timestamp()
            );
        }

    }

}