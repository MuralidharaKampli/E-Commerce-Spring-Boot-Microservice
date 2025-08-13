package com.mss.user_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mss.order_service.dto.UserDTO;
import com.mss.user_service.entity.UserRequest;
import com.mss.user_service.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	@Autowired
	private UserService service;

	@PostMapping("/register")
	public ResponseEntity<Object> addUser(@RequestBody UserRequest request) {
		return service.addUser(request.getRequest());
	}

	@GetMapping("/{userId}")
	public ResponseEntity<Object> fetchUser(@PathVariable Long userId) {
		return service.fetchUser(userId);
	}

	@PutMapping("/{userId}")
	public ResponseEntity<Object> updateUser(@PathVariable Long userId, @RequestBody UserRequest request) {
		return service.updateUser(userId, request.getRequest());
	}

	@DeleteMapping("/{userId}")
	public ResponseEntity<Object> deleteUser(@PathVariable Long userId) {
		return service.deleteUser(userId);
	}
	
	@GetMapping("/order-user/{id}")
    public UserDTO getUserById(@PathVariable Long id) {
		return service.getUserById(id);
	}
}
