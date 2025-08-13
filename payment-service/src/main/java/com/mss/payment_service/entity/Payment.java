package com.mss.payment_service.entity;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "M_MS_ORDER_PAYMENT")
public class Payment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long txnId;
	private String txnDate = ZonedDateTime.now(ZoneId.of("Asia/Kolkata")).format(DateTimeFormatter.ISO_DATE_TIME);
	private Double amount;
	private String status;
	private String txnType;
}
