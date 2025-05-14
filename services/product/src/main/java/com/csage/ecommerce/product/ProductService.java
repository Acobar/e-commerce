package com.csage.ecommerce.product;

import com.csage.ecommerce.product.exceptions.ProductNotFoundException;
import com.csage.ecommerce.product.exceptions.ProductPurchaseException;
import com.csage.ecommerce.product.repository.ProductRepository;
import com.google.common.collect.Sets;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper mapper;

    public Integer createProduct(@Valid ProductRequest request) {
        var product = mapper.toProduct(request);
        return productRepository.save(product).getId();
    }

    public List<ProductPurchaseResponse> purchaseProducts(@Valid List<ProductPurchaseRequest> productPurchaseRequests) {
        var purchasedProducts = new ArrayList<ProductPurchaseResponse>();

        var productIds =  productPurchaseRequests.stream().map(ProductPurchaseRequest::id).toList();
        var availableProducts = productRepository.findByIdInOrderById(productIds);

        //compare the lists and make sure we have the same in each
        if(!Sets.difference(Sets.newHashSet(productIds), Sets.newHashSet(availableProducts)).isEmpty()){
            throw new ProductPurchaseException("One or more products do not exist or are out of stock.");
        }


        productPurchaseRequests.forEach(productPurchaseRequest -> {
            //get the requested product from the available products
            var product = availableProducts.stream()
                    .filter(product1 -> product1.getId().equals(productPurchaseRequest.id()))
                    .findFirst()
                    .orElseThrow(()->new ProductNotFoundException("Product was not found for id: "+productPurchaseRequest.id()));

            //make sure we have enough stock to fulfill the request
            if(product.getAvailableQuantity() < productPurchaseRequest.quantity()){
                throw new ProductPurchaseException("Insufficient stock for product id: "+productPurchaseRequest.id());
            }

            //set the new available quantity and save it
            product.setAvailableQuantity(product.getAvailableQuantity() - productPurchaseRequest.quantity());
            productRepository.save(product);
            purchasedProducts.add(mapper.toProductPurchaseResponse(product, productPurchaseRequest));

        });

        return purchasedProducts;
    }

    public ProductResponse findById(Integer productId) {
    return productRepository.findById(productId)
            .map(mapper::toProductResponse)
            .orElseThrow(()->new ProductNotFoundException("Product was not found for id: "+productId));
    }

    public List<ProductResponse> findAll() {
        return productRepository.findAll().stream()
                .map(mapper::toProductResponse)
                .collect(Collectors.toList());
    }
}
