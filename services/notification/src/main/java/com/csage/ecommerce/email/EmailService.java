package com.csage.ecommerce.email;

import com.csage.ecommerce.kafka.order.Product;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    //we dont want to block the main thread when sending emails
    @Async
    public void sendPaymentSuccessEmail(String destinationEmail, String customerName, BigDecimal amount, String orderReference) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeHelper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
        mimeHelper.setFrom("csage@gmail.com");

        final String templateName = EmailTemplates.PAYMENT_CONFIRMATION.getTemplateName();
        Map<String, Object> variables = Map.of("customerName", customerName, "amount", amount, "orderReference", orderReference);

        Context context = new Context();
        context.setVariables(variables);
        mimeHelper.setSubject(EmailTemplates.PAYMENT_CONFIRMATION.getSubject());

        try {
            String htmlTemplate = templateEngine.process(templateName, context);
            mimeHelper.setTo(destinationEmail);
            log.info("Sending email to {} using template {}", destinationEmail, templateName);
            mailSender.send(mimeMessage);
            log.info("Email sent to {}", destinationEmail);
        } catch (MessagingException e) {
            log.warn("Unable to send email to {} using template {}", destinationEmail, templateName, e);
        }
    }

    //we dont want to block the main thread when sending emails
    @Async
    public void sendOrderConfirmationEmail(String destinationEmail, String customerName, BigDecimal amount, String orderReference, List<Product> products) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeHelper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
        mimeHelper.setFrom("csage@gmail.com");

        final String templateName = EmailTemplates.ORDER_CONFIRMATION.getTemplateName();
        Map<String, Object> variables = Map.of("customerName", customerName, "totalAmount", amount, "orderReference", orderReference, "products", products);

        Context context = new Context();
        context.setVariables(variables);
        mimeHelper.setSubject(EmailTemplates.ORDER_CONFIRMATION.getSubject());

        try {
            String htmlTemplate = templateEngine.process(templateName, context);
            mimeHelper.setTo(destinationEmail);
            log.info("Sending email to {} using template {}", destinationEmail, templateName);
            mailSender.send(mimeMessage);
            log.info("Email sent to {}", destinationEmail);
        } catch (MessagingException e) {
            log.warn("Unable to send email to {} using template {}", destinationEmail, templateName, e);
        }
    }
}
