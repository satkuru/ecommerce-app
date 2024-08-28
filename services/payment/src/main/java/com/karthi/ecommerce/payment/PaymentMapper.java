package com.karthi.ecommerce.payment;

import com.karthi.ecommerce.notification.PaymentNotification;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {
    public Payment toPayment(PaymentRequest request) {
        return Payment.builder()
                .id(request.id())
                .orderId(request.orderId())
                .amount(request.amount())
                .paymentMethod(request.paymentMethod())
                .build();
    }

    public PaymentNotification toNotification(PaymentRequest request) {
        return new PaymentNotification(
                request.orderReference(),
                request.amount(),
                request.paymentMethod(),
                request.customer().firstname(),
                request.customer().lastname(),
                request.customer().email());
    }
}
