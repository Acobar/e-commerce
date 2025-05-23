package com.csage.ecommerce.customer;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

//feign example; restemplate example in productClient
@FeignClient(name = "customer-service", url = "${application.config.customers-url}")
public interface CustomerClient {

    //same as the one in customer service
    @GetMapping("{customer-id}")
    Optional<CustomerResponse> findByCustomerId(@PathVariable("customer-id") String customerId);
}
