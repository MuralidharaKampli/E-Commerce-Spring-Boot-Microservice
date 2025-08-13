package com.mss.payment_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mss.order_service.dto.PaymentDTO;
import com.mss.payment_service.entity.Payment;
import com.mss.payment_service.repository.PaymentRepository;

@Service
public class PaymentService {
	@Autowired
	private PaymentRepository repository;
	
	public Long addPayment(PaymentDTO payment) {
		Payment payment1 = new Payment();
		payment1.setAmount(payment.getAmount());
		payment1.setTxnType(payment.getTxnType());
		Payment savedPayment = repository.save(payment1);
		if(savedPayment == null) {
			payment1.setStatus("Failed");
			repository.save(payment1);
		}else {
			payment1.setStatus("Success");
			repository.save(payment1);
		}
		return savedPayment.getTxnId();
	}
}
