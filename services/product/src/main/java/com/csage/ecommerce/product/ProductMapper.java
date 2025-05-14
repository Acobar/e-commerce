package com.csage.ecommerce.product;

import com.csage.ecommerce.category.Category;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

@Service
public class ProductMapper {
    public Product toProduct(@Valid ProductRequest request) {
        return Product.builder()
                .id(request.id())
                .price(request.price())
                .name(request.name())
                .availableQuantity(request.availableQuantity())
                .description(request.description())
                .category(Category.builder()
                        .id(request.category_id()).build())
                .build();

    }

    public ProductResponse toProductResponse(@NotNull Product product) {
        return new ProductResponse(
                product.getAvailableQuantity(),
                product.getCategory().getDescription(),
                product.getCategory().getId(),
                product.getCategory().getName(),
                product.getDescription(),
                product.getId(),
                product.getName(),
                product.getPrice()
        );
    }

    public ProductPurchaseResponse toProductPurchaseResponse(Product product, ProductPurchaseRequest productPurchaseRequest) {
        return new ProductPurchaseResponse(product.getId(), product.getName(), product.getDescription(), product.getPrice(), productPurchaseRequest.quantity());
    }
}
