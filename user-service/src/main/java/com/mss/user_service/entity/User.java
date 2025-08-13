package com.mss.user_service.entity;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
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
@Table(name = "M_MS_USER")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;
	private String name;
	private String email;
	private String password;
	private String role;
	private String createdAt = ZonedDateTime.now(ZoneId.of("Asia/Kolkata")).format(DateTimeFormatter.ISO_DATE_TIME);
	
	@OneToOne
	@JsonBackReference("request-user")
	@JoinColumn(name = "rid", referencedColumnName = "requestId")
	private Request userRef;
	
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	@JsonManagedReference
	private Address address;
}
