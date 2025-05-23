package com.csage.ecommerce.order.service;

import com.csage.ecommerce.order.Order;
import com.csage.ecommerce.order.OrderRequest;
import com.csage.ecommerce.order.OrderResponse;
import org.springframework.stereotype.Service;

@Service
public class OrderMapper {
    public static OrderResponse fromOrder(Order order) {
        return new OrderResponse(order.getId(), order.getReference(), order.getTotalAmount(), order.getPaymentMethod(), order.getCustomerId());
    }

    public Order toOrder(OrderRequest orderRequest) {
        return Order.builder().id(orderRequest.id())
                .customerId(orderRequest.customerId())
                .reference(orderRequest.reference())
                .totalAmount(orderRequest.amount())
                .paymentMethod(orderRequest.paymentMethod())
                .build();
    }
}
