package com.mss.order_service.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "product-service", url = "http://localhost:8081/products")
public interface ProductServiceClient {
    @GetMapping("/order-product/{id}/price")
    public double getProductPrice(@PathVariable("id") Long productId);
    
    @PutMapping("/{productId}/{stock}")
    public int updateProductStock(@PathVariable Long productId, @PathVariable Integer stock);
}
