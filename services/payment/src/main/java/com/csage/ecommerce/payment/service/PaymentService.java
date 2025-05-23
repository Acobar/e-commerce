package com.csage.ecommerce.payment.service;

import com.csage.ecommerce.notification.PaymentNotificationProducer;
import com.csage.ecommerce.notification.PaymentNotificationRequest;
import com.csage.ecommerce.payment.PaymentMapper;
import com.csage.ecommerce.payment.PaymentRequest;
import com.csage.ecommerce.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentNotificationProducer paymentNotificationProducer;
    private final PaymentRepository repository;
    private final PaymentMapper mapper;

    public Integer createPayment(PaymentRequest request) {
        var payment = this.repository.save(this.mapper.toPayment(request));

        this.paymentNotificationProducer.sendNotification(
                new PaymentNotificationRequest(
                        request.orderReference(),
                        request.amount(),
                        request.paymentMethod(),
                        request.customer().firstname(),
                        request.customer().lastname(),
                        request.customer().email()
                )
        );
        return payment.getId();
    }
}
