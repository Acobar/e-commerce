package com.csage.ecommerce.customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Validated
public class Address {
    private String street;
    private String houseNumber;
    private String city;
    private String state;
    private String zipCode;
    private String country;

}
