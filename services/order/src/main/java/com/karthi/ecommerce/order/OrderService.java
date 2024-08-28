package com.karthi.ecommerce.order;

import com.karthi.ecommerce.customer.CustomerClient;
import com.karthi.ecommerce.exception.BusinessException;
import com.karthi.ecommerce.kafka.OrderProducer;
import com.karthi.ecommerce.orderline.OrderLineRequest;
import com.karthi.ecommerce.orderline.OrderLineService;
import com.karthi.ecommerce.product.ProductClient;
import com.karthi.ecommerce.product.PurchaseRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderMapper mapper;
    private final OrderRepository repository;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;

    public Integer createOrder(OrderRequest request) {
        //Get the customer and check
        var customer = this.customerClient
                .findCustomerById(request.customerId())
                .orElseThrow(()->new BusinessException("Cannot create order: No Customer found for id:"+request.customerId()));

        //purchase the product
        var purchasedProducts = this.productClient.purchaseProducts(request.products());

        //save order
        var order = this.repository.save(mapper.toOrder(request));

        //save orderLine
        for(PurchaseRequest purchaseRequest :request.products()){
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity())
            );
        }

        //do the payment

        //Send notification of order confirmation
        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        order.getReference(),
                        order.getAmount(),
                        order.getPaymentMethod(),
                        customer,
                        purchasedProducts));

        return order.getId();
    }

    public List<OrderResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::fromOrder)
                .collect(Collectors.toList());

    }

    public OrderResponse findOrderById(Integer orderId) {
        return repository.findById(orderId)
                .map(mapper::fromOrder)
                .orElseThrow(()->new BusinessException("Order not found with Id:"+orderId));
    }
}
