package com.karthi.ecommerce.order;

import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class OrderMapper {

    public Order toOrder(OrderRequest request) {
        if(Objects.isNull(request)) {
            return null;
        }
        return Order.builder()
                .id(request.id())
                .reference(request.reference())
                .customerId(request.customerId())
                .amount(request.amount())
                .paymentMethod(request.paymentMethod())
                .build();

    }

    public OrderResponse fromOrder(Order order){
        return new OrderResponse(
                order.getId(),
                order.getReference(),
                order.getAmount(),
                order.getPaymentMethod(),
                order.getCustomerId()
        );
    }
}
