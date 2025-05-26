package com.csage.ecommerce.order.service;

import com.csage.ecommerce.customer.CustomerClient;
import com.csage.ecommerce.kafka.OrderConfirmation;
import com.csage.ecommerce.kafka.OrderProducer;
import com.csage.ecommerce.order.OrderRequest;
import com.csage.ecommerce.order.OrderResponse;
import com.csage.ecommerce.order.orderLine.OrderLineRequest;
import com.csage.ecommerce.order.orderLine.OrderLineService;
import com.csage.ecommerce.order.repository.OrderRepository;
import com.csage.ecommerce.payment.PaymentClient;
import com.csage.ecommerce.payment.PaymentRequest;
import com.csage.ecommerce.product.ProductClient;
import com.csage.ecommerce.product.PurchaseRequest;
import com.csage.ecommerce.product.PurchaseResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;
    private final PaymentClient paymentClient;

    public Integer createOrder(@Valid OrderRequest orderRequest) {

        //check the customer (open feign example)
        var customerResponse = customerClient.findByCustomerId(orderRequest.customerId()).orElseThrow(() -> new BusinessException("Cannot create the order: customer not found"));

        //purchase product from products microservice (rest template example)
        List<PurchaseResponse> purchaseResponses = productClient.purchaseProducts(orderRequest.products());

        //persist order
        var order = this.orderRepository.save(orderMapper.toOrder(orderRequest));

        //persist order lines
        for (PurchaseRequest purchaseRequest : orderRequest.products()) {
            orderLineService.saveOrderLine(
                    new OrderLineRequest(null, order.getId(), purchaseRequest.productId(), purchaseRequest.quantity())
            );
        }

        //initiate the payment request
        var paymentRequest = new PaymentRequest(order.getTotalAmount(), order.getPaymentMethod(), order.getId(), order.getReference(), customerResponse);
        paymentClient.requestOrderPayment(paymentRequest);

        //send order confirmation to notification microservice (kafka)
        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        orderRequest.reference(), orderRequest.amount(),
                        orderRequest.paymentMethod(), customerResponse, purchaseResponses)
        );
        return order.getId();
    }

    public List<OrderResponse> findAll() {
        return orderRepository.findAll().stream().map(OrderMapper::fromOrder).collect(Collectors.toList());
    }


    public OrderResponse findById(Integer orderId) {
        return orderRepository.findById(orderId).map(OrderMapper::fromOrder).orElseThrow(() -> new EntityNotFoundException(String.format("No order found with the order id %d", orderId)));
    }
}
