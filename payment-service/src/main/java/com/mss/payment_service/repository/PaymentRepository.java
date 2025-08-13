package com.mss.payment_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mss.payment_service.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long>{

}
