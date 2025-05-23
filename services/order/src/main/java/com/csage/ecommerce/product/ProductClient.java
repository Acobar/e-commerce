package com.csage.ecommerce.product;

import com.csage.ecommerce.order.service.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductClient {
    private final RestTemplate restTemplate;
    @Value("${application.config.products-url}")
    private String productUrl;

    public List<PurchaseResponse> purchaseProducts(List<PurchaseRequest> purchaseRequests) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<List<PurchaseRequest>> request = new HttpEntity<>(purchaseRequests, headers);

        //this will let the restTemplate parse the response automatically to this type
        ParameterizedTypeReference<List<PurchaseResponse>> typeRef = new ParameterizedTypeReference<>() {
        };
        ResponseEntity<List<PurchaseResponse>> responseEntity = restTemplate.exchange(
                productUrl + "/purchase", HttpMethod.POST, request, typeRef);

        if (responseEntity.getStatusCode().isError()) {
            throw new BusinessException("An error occurred while purchasing products.  Error code: " + responseEntity.getStatusCode());
        }
        return responseEntity.getBody();

    }

}
