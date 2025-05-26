package com.csage.ecommerce.kafka.payment;

import java.math.BigDecimal;

//must be the same as PaymentNotificationRequest
public record PaymentConfirmation(
        String orderReference,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        String customerFirstName,
        String customerLastName,
        String customerEmail
) {
}
