package com.csage.ecommerce.exception.handler;

import java.util.Map;

public record ErrorResponse(Map<String, String> errors) {
}
