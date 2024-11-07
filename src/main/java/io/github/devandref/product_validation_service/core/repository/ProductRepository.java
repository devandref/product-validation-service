package io.github.devandref.product_validation_service.core.repository;

import io.github.devandref.product_validation_service.core.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    Boolean existsByCode(String code);

}
