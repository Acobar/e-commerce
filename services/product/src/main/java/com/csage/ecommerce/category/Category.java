package com.csage.ecommerce.category;

import com.csage.ecommerce.product.Product;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class Category {
    @Id
    private Integer id;
    private String name;
    private String description;
    @OneToMany(mappedBy = "id", cascade = CascadeType.REMOVE)
    private List<Product> products;
}
