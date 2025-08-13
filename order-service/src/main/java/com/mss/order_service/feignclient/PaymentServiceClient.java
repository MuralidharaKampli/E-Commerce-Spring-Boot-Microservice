package com.mss.order_service.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.mss.order_service.dto.PaymentDTO;

@FeignClient(name = "payment-service", url = "http://localhost:8084/payment")
public interface PaymentServiceClient {
	@PostMapping("/save")
	public Long addPayment(@RequestBody PaymentDTO payment);
}
