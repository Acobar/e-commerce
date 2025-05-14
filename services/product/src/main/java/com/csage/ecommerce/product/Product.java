package com.csage.ecommerce.product;

import com.csage.ecommerce.category.Category;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class Product {
    @Id
    private Integer id;
    private String name;
    private String description;
    private BigDecimal price;
    private Long availableQuantity;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

}
