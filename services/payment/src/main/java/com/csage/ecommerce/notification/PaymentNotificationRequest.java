package com.csage.ecommerce.notification;

import com.csage.ecommerce.payment.PaymentMethod;

import java.math.BigDecimal;

//must be the same as PaymentConfirmation
public record PaymentNotificationRequest(
        String orderReference,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        String customerFirstname,
        String customerLastname,
        String customerEmail
) {
}
