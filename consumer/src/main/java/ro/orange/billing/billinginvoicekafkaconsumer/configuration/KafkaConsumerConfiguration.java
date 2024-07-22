package ro.orange.billing.billinginvoicekafkaconsumer.configuration;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.util.backoff.FixedBackOff;
import ro.orange.billing.billinginvoicecore.kafka.KafkaPayload;
import ro.orange.billing.billinginvoicekafkaconsumer.service.KafkaRetryListener;

import java.util.HashMap;
import java.util.Map;

import static org.apache.kafka.clients.consumer.ConsumerConfig.*;
import static org.springframework.kafka.listener.ContainerProperties.AckMode.RECORD;
import static org.springframework.kafka.support.serializer.ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS;
import static org.springframework.kafka.support.serializer.ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS;
import static org.springframework.kafka.support.serializer.JsonDeserializer.TRUSTED_PACKAGES;
import static org.springframework.kafka.support.serializer.JsonDeserializer.VALUE_DEFAULT_TYPE;


@Slf4j
@Data
@EnableKafka
@EnableRetry
@Configuration
@RequiredArgsConstructor
public class KafkaConsumerConfiguration {

    public static final String INVOICE_KAFKA_PAYLOAD_DTO_PACKAGE = "ro.orange.billing.billinginvoicecore.kafka.KafkaPayload";
    public static final String INVOICE_KAFKA_CONSUMER_MAX_POLL_INTERVAL = "60000";
    public static final String INVOICE_KAFKA_CONSUMER_AUTO_OFFSET_RESET = "earliest";
    public static final boolean INVOICE_KAFKA_CONSUMER_ENABLE_AUTO_COMMIT = false;
    public static final String INVOICE_KAFKA_CONSUMER_TRUSTED_PACKAGES = "*";

    @Value("${kafka.groupId}")
    private String groupId;
    @Value("${kafka.bootstrapServers}")
    private String bootstrapServers;
    @Value("${kafka.backOff}")
    private long backOff;
    @Value("${kafka.maxRetryAttempts}")
    private int maxRetryAttempts;
    @Value("${kafka.concurrency}")
    private int concurrency;
    @Value("${kafka.allowAutoCreateTopics}")
    private String allowAutoCreateTopics;
    @Value("${kafka.setMissingTopicsFatal}")
    private boolean setMissingTopicsFatal;

    private final KafkaRetryListener kafkaRetryListener;


    @Bean
    public ConsumerFactory<String, KafkaPayload> consumerFactory() {

        Map<String, Object> config = new HashMap<>();

        config.put(BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(GROUP_ID_CONFIG, groupId);
        config.put(AUTO_OFFSET_RESET_CONFIG, INVOICE_KAFKA_CONSUMER_AUTO_OFFSET_RESET);
        config.put(ALLOW_AUTO_CREATE_TOPICS_CONFIG, allowAutoCreateTopics);
        config.put(ENABLE_AUTO_COMMIT_CONFIG, INVOICE_KAFKA_CONSUMER_ENABLE_AUTO_COMMIT);
        config.put(MAX_POLL_INTERVAL_MS_CONFIG, INVOICE_KAFKA_CONSUMER_MAX_POLL_INTERVAL);

        config.put(KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        config.put(VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        config.put(KEY_DESERIALIZER_CLASS, StringDeserializer.class);
        config.put(VALUE_DESERIALIZER_CLASS, JsonDeserializer.class);

        config.put(VALUE_DEFAULT_TYPE, INVOICE_KAFKA_PAYLOAD_DTO_PACKAGE);
        config.put(TRUSTED_PACKAGES, INVOICE_KAFKA_CONSUMER_TRUSTED_PACKAGES);

        return new DefaultKafkaConsumerFactory<>(config);

    }

    @Bean
    public DefaultErrorHandler defaultErrorHandler() {

        DefaultErrorHandler defaultErrorHandler = new DefaultErrorHandler(new FixedBackOff(backOff, maxRetryAttempts));

         defaultErrorHandler.setRetryListeners(kafkaRetryListener);

        return defaultErrorHandler;

    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, KafkaPayload> kafkaListenerContainerFactory(
            ConsumerFactory<String, KafkaPayload> consumerFactory,
            DefaultErrorHandler defaultErrorHandler
    ) {

        ConcurrentKafkaListenerContainerFactory<String, KafkaPayload> factory = new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(consumerFactory);
        factory.setCommonErrorHandler(defaultErrorHandler);

        factory.setMissingTopicsFatal(setMissingTopicsFatal);
        factory.setConcurrency(concurrency);
        factory.getContainerProperties().setAckMode(RECORD);

        return factory;

    }

}











