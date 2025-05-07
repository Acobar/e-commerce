package com.csage.ecommerce.customer.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CustomerNotFoundException extends RuntimeException {
    private final String msg;
}
