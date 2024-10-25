package io.github.devandref.product_validation_service.config.exeception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionGlobalHandler {

    public ResponseEntity<?> handleValidationException(ValidationException validationException) {
        var details = new ExceptionsDetails(HttpStatus.BAD_REQUEST.value(), validationException.getMessage());
        return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
    }

}
