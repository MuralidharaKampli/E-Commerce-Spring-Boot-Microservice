package com.mss.product_service.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "M_MS_PRODUCT_REQUEST")
public class Request {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long requestId;
	@OneToOne(mappedBy = "prodRef", cascade = CascadeType.ALL)
	@JsonManagedReference("request-product")
	private Product product;
}
