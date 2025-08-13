package com.mss.payment_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mss.order_service.dto.PaymentDTO;
import com.mss.payment_service.service.PaymentService;

@RestController
@RequestMapping("/payment")
public class PaymentController {
	
	@Autowired
	private PaymentService service;
	
	@PostMapping("/save")
	public Long addPayment(@RequestBody PaymentDTO payment) {
		return service.addPayment(payment);
	}
}
