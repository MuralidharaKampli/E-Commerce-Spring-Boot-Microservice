package com.mss.product_service.dto;

import lombok.Data;

@Data
public class Response {
	private String message;
	private ProductDTO product;
}
