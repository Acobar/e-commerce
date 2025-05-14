package com.csage.ecommerce.product;

import com.csage.ecommerce.category.Category;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProductPurchaseRequest (
        @NotNull(message = "Product id is required")
        Integer id,
        @Positive(message = "Quantity must be a positive number")
        Long quantity
){
}
