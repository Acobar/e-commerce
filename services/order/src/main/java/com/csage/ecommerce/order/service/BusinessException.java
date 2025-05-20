package com.csage.ecommerce.order.service;


import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class BusinessException extends RuntimeException {

    public BusinessException(String error) {
        super(error);
    }
}
