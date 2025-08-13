package com.mss.order_service.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "M_MS_ORDER_ADDRESS")
public class Address {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long addressId;
	private String street;
	private String district;
	private String state;
	private String country;
	private Integer pincode;
	
	@OneToOne
	@JsonBackReference
	@JoinColumn(name = "ordId", referencedColumnName = "orderId")
	private Order order;
}
