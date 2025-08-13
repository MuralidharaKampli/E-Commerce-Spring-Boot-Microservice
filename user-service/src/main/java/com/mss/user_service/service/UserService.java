package com.mss.user_service.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mss.order_service.dto.UserDTO;
import com.mss.user_service.dto.Message;
import com.mss.user_service.dto.Response;
import com.mss.user_service.dto.UserResponse;
import com.mss.user_service.entity.Address;
import com.mss.user_service.entity.Request;
import com.mss.user_service.entity.User;
import com.mss.user_service.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository repository;

	@Autowired
	private ModelMapper mapper;

	public ResponseEntity<Object> addUser(Request request) {
		Optional<User> userData = repository.findByEmail(request.getUser().getEmail());
		UserResponse userResponse = new UserResponse();
		Response response = new Response();
		com.mss.user_service.dto.User userdto = new com.mss.user_service.dto.User();
		if (userData.isEmpty()) {
			Request requestSave = repository.save(request);
			mapper.map(requestSave.getUser(), userdto);
			response.setMessage("User stored successfully");
			response.setUser(userdto);
			userResponse.setResponse(response);
			return ResponseEntity.status(HttpStatus.OK).body(userResponse);
		} else {
			response.setMessage("User stored unsuccessfully, user already exists");
			userResponse.setResponse(response);
			return ResponseEntity.status(HttpStatus.CONFLICT).body(userResponse);
		}
	}

	public ResponseEntity<Object> fetchUser(Long userId) {
		Optional<User> userData = repository.findByUserId(userId);
		UserResponse userResponse = new UserResponse();
		Response response = new Response();
		com.mss.user_service.dto.User userdto = new com.mss.user_service.dto.User();
		if (userData.isPresent()) {
			User userFound = userData.get();
			mapper.map(userFound, userdto);
			response.setMessage("User fetched successfully");
			response.setUser(userdto);
			userResponse.setResponse(response);
			return ResponseEntity.status(HttpStatus.OK).body(userResponse);
		} else {
			response.setMessage("User fetch unsuccessfully, user id not exists");
			userResponse.setResponse(response);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(userResponse);
		}
	}

	public ResponseEntity<Object> updateUser(Long userId, Request request) {
		Optional<Request> request2 = repository.findById(userId);
		Message message = new Message();
		if (request2.isPresent()) {
			User user = request2.get().getUser();
			Request request3 = request2.get();
			user.setName(request.getUser().getName());
			user.setEmail(request.getUser().getEmail());
			user.setRole(request.getUser().getRole());
			user.setCreatedAt(user.getCreatedAt());
			
			Address address = user.getAddress();
			address.setStreet(request.getUser().getAddress().getStreet());
			address.setDistrict(request.getUser().getAddress().getDistrict());
			address.setState(request.getUser().getAddress().getState());
			address.setCountry(request.getUser().getAddress().getCountry());
			address.setPincode(request.getUser().getAddress().getPincode());
			user.setAddress(address);
			request3.setUser(user);
			message.setMessage("User updated successfully");
			repository.save(request3);
			return ResponseEntity.status(HttpStatus.OK).body(message);
		} else {
			message.setMessage("User update unsuccessfully, user id not exists");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
		}
	}

	public ResponseEntity<Object> deleteUser(Long userId) {
		Optional<User> userData = repository.findByUserId(userId);
		Message message = new Message();
		if (userData.isPresent()) {
			message.setMessage("User deleted successfully");
			repository.deleteById(userId);
			return ResponseEntity.status(HttpStatus.OK).body(message);
		} else {
			message.setMessage("User delete unsuccessfully, user id not exists");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
		}
	}

	public UserDTO getUserById(Long id) {
		Optional<User> userData = repository.findByUserId(id);
		UserDTO user = new UserDTO();
		if(userData.isPresent()) {
			User user2 = userData.get();
			user.setId(user2.getUserId());
			user.setName(user2.getName());
			user.setEmail(user2.getEmail());
			return user;
		}else {
			return null;
		}
	}
}
