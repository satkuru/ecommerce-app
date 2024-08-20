package com.karthi.ecommerce.product.handler;

import java.util.Map;

public record ErrorResponse(
        Map<String,String> errors
) {
}
