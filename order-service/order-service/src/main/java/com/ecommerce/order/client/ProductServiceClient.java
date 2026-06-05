package com.ecommerce.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;

@FeignClient(name = "product-service")
public interface ProductServiceClient {

    @GetMapping("/api/products/{id}")
    ProductDTO getProduct(@PathVariable ("id") Long id);

    @PostMapping("/api/products/check-availability")
    AvailabilityResponse checkAvailability(@RequestBody AvailabilityRequest request);

    @PostMapping("api/products/reduce-quantity")
    void reduceQuantity(@RequestBody AvailabilityRequest request);
}

