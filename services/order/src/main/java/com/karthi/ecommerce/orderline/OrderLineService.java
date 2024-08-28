package com.karthi.ecommerce.orderline;


import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RequiredArgsConstructor
@Service
public class OrderLineService {
    private final OrderLineMapper mapper;
    private final OrderLineRepository repository;
    public Integer saveOrderLine(OrderLineRequest orderLineRequest) {
        OrderLine orderLine = mapper.toOrderLine(orderLineRequest);
        return repository.save(orderLine).getId();
    }

    public List<OrderLineResponse> findAllByOrderId(Integer orderId) {
        List<OrderLine> orderLinesByOrderId = repository.findAllByOrderId(orderId);
        return orderLinesByOrderId
                .stream()
                .map(mapper::fromOrderLine)
                .collect(Collectors.toList());
    }
}
