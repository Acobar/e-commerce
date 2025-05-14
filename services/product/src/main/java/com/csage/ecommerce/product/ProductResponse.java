package com.csage.ecommerce.product;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.math.BigDecimal;

public record ProductResponse (
        Long availableQuantity,
        String categoryDescription,
        Integer categoryId,
        String categoryName,
        String description,
        Integer id,
        String name,
        BigDecimal price
){}
