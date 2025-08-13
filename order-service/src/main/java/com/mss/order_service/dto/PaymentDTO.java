package com.mss.order_service.dto;

import lombok.Data;

@Data
public class PaymentDTO {
	private Long txnId;
	private String txnDate;
	private Double amount;
	private String status;
	private String txnType;
}
