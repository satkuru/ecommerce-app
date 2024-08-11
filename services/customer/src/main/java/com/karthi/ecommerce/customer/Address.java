package com.karthi.ecommerce.customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@AllArgsConstructor
@NoArgsConstructor
@Validated
@Data
@Builder
public class Address {
    private String houseNumberName;
    private String streetName;
    private String city;
    private String postcode;
}
