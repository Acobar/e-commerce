package com.csage.ecommerce.product.exceptions;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String s) {
        super(s);
    }
}
