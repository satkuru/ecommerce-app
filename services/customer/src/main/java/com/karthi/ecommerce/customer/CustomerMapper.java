package com.karthi.ecommerce.customer;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CustomerMapper {
    public Customer toCustomer(CustomerRequest request) {
        if(request==null){
            return null;
        }
        return Customer.builder()
                .id(UUID.randomUUID().toString())
                .firstname(request.firstname())
                .lastname(request.lastname())
                .email(request.email())
                .address(request.address())
                .build();
    }

    public CustomerResponse fromCustomer(Customer customer) {
        return new CustomerResponse(
                customer.getId(),
                customer.getFirstname(),
                customer.getLastname(),
                customer.getEmail(),
                customer.getAddress());
    }
}
