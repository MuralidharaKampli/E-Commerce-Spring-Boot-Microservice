package com.mss.user_service.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "M_MS_USER_REQUEST")
public class Request {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long requestId;
	@OneToOne(mappedBy = "userRef", cascade = CascadeType.ALL)
	@JsonManagedReference("request-user")
	private User user;
}
