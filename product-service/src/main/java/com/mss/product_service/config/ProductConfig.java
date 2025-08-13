package com.mss.product_service.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductConfig {
	@Bean
	public ModelMapper beanCreation() {
		return new ModelMapper();
	}
}
