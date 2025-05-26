package com.csage.ecommerce.kafka;

import com.csage.ecommerce.email.EmailService;
import com.csage.ecommerce.kafka.order.OrderConfirmation;
import com.csage.ecommerce.kafka.payment.PaymentConfirmation;
import com.csage.ecommerce.notification.Notification;
import com.csage.ecommerce.notification.NotificationRepository;
import com.csage.ecommerce.notification.NotificationType;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {
    private final NotificationRepository notificationRepository;
    private final EmailService emailService;

    @KafkaListener(topics = "payment-topic")
    public void consumePaymentSuccessNotification(PaymentConfirmation paymentConfirmation) throws MessagingException {
        log.info("Received payment success notification from payment-topic with body = < {} >", paymentConfirmation);
        notificationRepository.save(
                Notification.builder()
                        .notificationType(NotificationType.PAYMENT_CONFIRMATION)
                        .notificationDate(LocalDateTime.now())
                        .paymentConfirmation(paymentConfirmation)
                        .build());

        emailService.sendPaymentSuccessEmail(paymentConfirmation.customerEmail(),
                paymentConfirmation.customerFirstName() + " " + paymentConfirmation.customerLastName(),
                paymentConfirmation.amount(), paymentConfirmation.orderReference());
    }

    @KafkaListener(topics = "order-topic")
    public void consumeOrderConfirmationNotification(OrderConfirmation orderConfirmation) throws MessagingException {
        log.info("Received order notification from order-topic with body = < {} >", orderConfirmation);
        notificationRepository.save(
                Notification.builder()
                        .notificationType(NotificationType.ORDER_NOTIFICATION)
                        .notificationDate(LocalDateTime.now())
                        .orderConfirmation(orderConfirmation)
                        .build());

        emailService.sendOrderConfirmationEmail(orderConfirmation.customer().email(),
                orderConfirmation.customer().firstName() + " " + orderConfirmation.customer().lastName(),
                orderConfirmation.totalAmount(), orderConfirmation.orderReference(), orderConfirmation.products());


    }
}
