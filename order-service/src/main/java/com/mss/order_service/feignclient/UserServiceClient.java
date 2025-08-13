package com.mss.order_service.feignclient;

import com.mss.order_service.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", url = "http://localhost:8080/users") 
public interface UserServiceClient {
    @GetMapping("/order-user/{id}")
    public UserDTO getUserById(@PathVariable Long id);
}
