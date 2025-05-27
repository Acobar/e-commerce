package com.csage.ecommerce.orderline;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record OrderLineRequest(
        Integer id,
        Integer orderId,
        @NotNull(message = "Product id is required") Integer productId,
        @Positive(message = "Quantity must be a positive number") Long quantity) {
}
