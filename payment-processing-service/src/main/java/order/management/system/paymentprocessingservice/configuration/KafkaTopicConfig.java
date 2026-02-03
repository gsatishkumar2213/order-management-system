package order.management.system.paymentprocessingservice.configuration;

import order.management.system.paymentprocessingservice.events.OrderCreatedEvent;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.FixedBackOff;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public DefaultErrorHandler errorHandler(KafkaOperations<Long, OrderCreatedEvent> template) {
        return new DefaultErrorHandler(
                new DeadLetterPublishingRecoverer(template),
                new FixedBackOff(1000L, 2)
        );
    }

    @Bean
    public NewTopic paymentProcessedTopic() {
        return TopicBuilder.name("payment-processed")
                           .partitions(1)
                           .replicas(1)
                           .build();
    }

    @Bean
    public NewTopic paymentProcessingDLT() {
        return TopicBuilder.name("payment-processed-dlt")
                           .partitions(1)
                           .replicas(1)
                           .build();
    }
}