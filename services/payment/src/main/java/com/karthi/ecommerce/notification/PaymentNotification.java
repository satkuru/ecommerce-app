package com.karthi.ecommerce.notification;

import com.karthi.ecommerce.payment.PaymentMethod;

import java.math.BigDecimal;

public record PaymentNotification(
        String orderReference,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        String firstname,
        String lastname,
        String email
) {
}
