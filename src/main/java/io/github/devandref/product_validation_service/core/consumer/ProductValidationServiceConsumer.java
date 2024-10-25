package io.github.devandref.product_validation_service.core.consumer;

import io.github.devandref.product_validation_service.core.utils.JsonUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class ProductValidationServiceConsumer {

    private final JsonUtil jsonUtil;

    @KafkaListener(
            groupId = "${spring.kafka.consumer.group-id}",
            topics = "${spring.kafka.topic.orchestrator}"
    )
    public void consumerOrchestratorEvent(String payload) {
        log.info("Receiving event {} from orchestrator topic", payload);
        var event = jsonUtil.toEvent(payload);
        log.info("Event object {}", event);
    }

    @KafkaListener(
            groupId = "${spring.kafka.consumer.group-id}",
            topics = "${spring.kafka.topic.product-validation-success}"
    )
    public void consumerProductValidationSuccessEvent(String payload) {
        log.info("Receiving event {} from validation-success topic", payload);
        var event = jsonUtil.toEvent(payload);
        log.info("Event object {}", event);
    }

    @KafkaListener(
            groupId = "${spring.kafka.consumer.group-id}",
            topics = "${spring.kafka.topic.product-validation-fail}"
    )
    public void consumerProductValidationFailEvent(String payload) {
        log.info("Receiving event {} from validation-fail topic", payload);
        var event = jsonUtil.toEvent(payload);
        log.info("Event object {}", event);
    }


}
