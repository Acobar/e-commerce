package com.csage.ecommerce.customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Customer {
    @Id private String id;
    private String firstName;
    private String lastName;
    private String email;
    private Address address;
}
