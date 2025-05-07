package com.csage.ecommerce.customer;

import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public class CustomerMapper {
    public Customer toCustomer(@Valid CustomerRequest request) {
        if(request != null) {
            return Customer.builder()
                    .id(request.id())
                    .firstName(request.firstname())
                    .lastName(request.lastname())
                    .email(request.email())
                    .address(request.address())
                    .build();
        }else {
            return Customer.builder().build();
        }
    }

    public CustomerResponse fromCustomer(Customer customer) {
        return new CustomerResponse(customer.getId(), customer.getFirstName(), customer.getLastName(), customer.getEmail(), customer.getAddress());
    }
}
