package com.csage.ecommerce.product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProductRequest(

        Integer id,
        @NotNull(message = "Product name is required")
        String name,
        @NotNull(message = "Product description is required")
        String description,
        @Positive(message = "Product price must be a positive number")
        BigDecimal price,
        Long availableQuantity,
        @NotNull(message = "Product category id is required")
        Integer category_id
) {
}
