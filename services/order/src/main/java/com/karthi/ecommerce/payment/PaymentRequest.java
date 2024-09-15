package com.karthi.ecommerce.payment;

import com.karthi.ecommerce.customer.CustomerResponse;
import com.karthi.ecommerce.order.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        CustomerResponse customer
) {
}
