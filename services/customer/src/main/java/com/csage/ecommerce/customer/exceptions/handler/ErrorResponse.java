package com.csage.ecommerce.customer.exceptions.handler;

import java.util.Map;

public record ErrorResponse(Map<String, String> errors) {
}
