package com.mss.user_service.dto;

import lombok.Data;

@Data
public class User {
	private Long id;
	private String name;
	private String email;
	private String role;
	private String createdAt;
	private Address address;
}
