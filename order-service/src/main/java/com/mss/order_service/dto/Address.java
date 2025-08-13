package com.mss.order_service.dto;

import lombok.Data;

@Data
public class Address {
	private Long addressId;
	private String street;
	private String district;
	private String state;
	private String country;
	private Integer pincode;
}

