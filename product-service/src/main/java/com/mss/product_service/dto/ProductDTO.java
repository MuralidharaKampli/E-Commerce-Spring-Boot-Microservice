package com.mss.product_service.dto;

import lombok.Data;

@Data
public class ProductDTO {
	private Long productId;
	private String name;
	private String description;
	private Double price;
	private Integer stock;
	private String category;
	private Double rating;
}
