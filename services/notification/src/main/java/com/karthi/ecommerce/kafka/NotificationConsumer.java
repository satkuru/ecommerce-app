package com.karthi.ecommerce.kafka;

import com.karthi.ecommerce.email.EmailService;
import com.karthi.ecommerce.kafka.order.OrderConfirmation;
import com.karthi.ecommerce.kafka.payment.PaymentConfirmation;
import com.karthi.ecommerce.notification.Notification;
import com.karthi.ecommerce.notification.NotificationRepository;
import com.karthi.ecommerce.notification.NotificationType;
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
    private final NotificationRepository repository;
    private final EmailService emailService;

    @KafkaListener(topics = "payment-topic")
    public void consumePaymentConfirmation(PaymentConfirmation confirmation) throws MessagingException {
        log.info("Received payment confirmation on topic: {} for order with reference {}","payment-topic",confirmation.orderReference());
        repository.save(Notification.builder()
                .notificationType(NotificationType.PAYMENT_CONFIRMATION)
                .notificationDate(LocalDateTime.now())
                .paymentConfirmation(confirmation)
                .build());
        log.info("Sending email confirmation of payment for order with ref:{}",confirmation.orderReference());
        var customerName = confirmation.firstname()+" "+confirmation.lastname();
        emailService.sendPaymentConfirmationEmail(confirmation.email(), customerName,confirmation.amount(), confirmation.orderReference());
    }

    @KafkaListener(topics = "order-topic")
    public void consumeOrderConfirmation(OrderConfirmation confirmation) throws MessagingException {
        log.info("Received order confirmation on topic: {} for order with reference {}","order-topic",confirmation.orderReference());
        repository.save(Notification.builder()
                .notificationType(NotificationType.ORDER_CONFIRMATION)
                .notificationDate(LocalDateTime.now())
                .orderConfirmation(confirmation)
                .build());
        log.info("Sending email confirmation of order with reference: {}",confirmation.orderReference());
        var customerName = confirmation.customer().firstname()+" "+confirmation.customer().lastname();
        emailService.sendOrderConfirmationEmail(confirmation.customer().email(),
                customerName,confirmation.totalAmount(),
                confirmation.orderReference(),
                confirmation.products());
    }
}
