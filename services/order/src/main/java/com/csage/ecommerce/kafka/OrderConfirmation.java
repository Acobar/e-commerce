package com.csage.ecommerce.kafka;

import com.csage.ecommerce.customer.CustomerResponse;
import com.csage.ecommerce.order.PaymentMethod;
import com.csage.ecommerce.product.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
   String orderReference,
   BigDecimal totalAmount,
   PaymentMethod paymentMethod,
   CustomerResponse customer,
   List<PurchaseResponse> purchasedProducts

) {
}
