package io.github.devandref.product_validation_service.core.service;

import io.github.devandref.product_validation_service.config.exeception.ValidationException;
import io.github.devandref.product_validation_service.core.dto.Event;
import io.github.devandref.product_validation_service.core.producer.SagaProducer;
import io.github.devandref.product_validation_service.core.repository.ProductRepository;
import io.github.devandref.product_validation_service.core.repository.ValidationRepository;
import io.github.devandref.product_validation_service.core.utils.JsonUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;


@Slf4j
@Service
@AllArgsConstructor
public class ProductValidationService {

    private final JsonUtil jsonUtil;
    private final SagaProducer producer;
    private final ProductRepository productRepository;
    private final ValidationRepository validationRepository;

    public void validateExistingProducts(Event event) {
        try {
            checkCurrentValidation(event);
            createValidation(event);
            handleSuccess(event);
        } catch (Exception ex) {
            log.error("Error trying to validate products: ", ex);
            handleFailCurrentNotExecuted(event, ex.getMessage());
        }
        producer.sendEvent(jsonUtil.toJson(event));
    }

    private void checkCurrentValidation(Event event) {
        if(ObjectUtils.isEmpty(event.getPayload()) || ObjectUtils.isEmpty(event.getPayload().getProducts())) {
            throw new ValidationException("Product list is empty!");
        }

        if(ObjectUtils.isEmpty(event.getPayload().getId()) || ObjectUtils.isEmpty(event.getPayload().getTransactionalId())) {
            throw new ValidationException("OrderID and TransactionID must be informed!");
        }
    }


}
