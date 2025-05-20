package com.csage.ecommerce.product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ProductPurchaseRequest(
        @NotNull(message = "Product id is required")
        Integer id,
        @Positive(message = "Quantity must be a positive number")
        Long quantity
) {
}
