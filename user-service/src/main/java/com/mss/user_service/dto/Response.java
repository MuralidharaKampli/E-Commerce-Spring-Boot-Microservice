package com.mss.user_service.dto;

import lombok.Data;

@Data
public class Response {
	private String message;
	private User user;
}
