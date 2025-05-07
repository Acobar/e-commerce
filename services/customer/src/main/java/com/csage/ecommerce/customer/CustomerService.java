package com.csage.ecommerce.customer;

import com.csage.ecommerce.customer.exceptions.CustomerNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public String createCustomer(@Valid CustomerRequest customerRequest) {
        return customerRepository.save(customerMapper.toCustomer(customerRequest)).getId();
    }

    public void updateCustomer(@Valid CustomerRequest customerRequest) {
        Customer customer = customerRepository.findById(customerRequest.id())
                .orElseThrow(()->new CustomerNotFoundException(String.format("Unable to update customer: No customer with id [%s]",customerRequest.id())));

        mergeCustomer(customer, customerRequest);
        customerRepository.save(customer);
    }

    private void mergeCustomer(Customer customer, @Valid CustomerRequest customerRequest) {
        if(StringUtils.isNotBlank(customerRequest.firstname())) {
            customer.setFirstName(customerRequest.firstname());
        }
        if(StringUtils.isNotBlank(customerRequest.lastname())) {
            customer.setLastName(customerRequest.lastname());
        }
        if(StringUtils.isNotBlank(customerRequest.email())) {
            customer.setEmail(customerRequest.email());
        }
        if(customerRequest.address() != null) {
            customer.setAddress(customerRequest.address());
        }
    }

    public List<CustomerResponse> findAllCustomers() {
        return customerRepository.findAll().stream().map(customerMapper::fromCustomer).collect(Collectors.toList());
    }

    public Boolean existsById(String customerId) {
        return customerRepository.existsById(customerId);
    }

    public Customer findById(String customerId) {
        return customerRepository.findById(customerId).orElseThrow( ()-> new CustomerNotFoundException(String.format("Unable to find customer: No customer with id [%s]",customerId)));
    }

    public void deleteCustomer(@Valid CustomerRequest customerRequest) {
        customerRepository.deleteById(customerRequest.id());
    }
}
