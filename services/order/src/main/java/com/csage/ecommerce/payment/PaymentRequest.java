package com.csage.ecommerce.payment;

import com.csage.ecommerce.customer.CustomerResponse;
import com.csage.ecommerce.order.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        CustomerResponse customerResponse
) {
}
