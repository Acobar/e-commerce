package com.csage.ecommerce.product.exceptions.handler;

import java.util.Map;

public record ErrorResponse(Map<String, String> errors) {
}
