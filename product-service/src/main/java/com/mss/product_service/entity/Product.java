package com.mss.product_service.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "M_MS_PRODUCT")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq")
	@SequenceGenerator(name = "product_seq", sequenceName = "M_MS_PRODUCT_SEQUENCE", initialValue = 100, allocationSize = 1)
	private Long productId;
	private String name;
	private String description;
	private Double price;
	private Integer stock;
	private String category;
	private Double rating;
	@OneToOne
	@JsonBackReference("request-product")
	@JoinColumn(name = "rid", referencedColumnName = "requestId")
	private Request prodRef;
}
