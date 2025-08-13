package com.mss.product_service.controller;

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

import com.mss.product_service.entity.ProductRequest;
import com.mss.product_service.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {
	
	@Autowired
	private ProductService service;
	
	@PostMapping("/save")
	public ResponseEntity<Object> addProduct(@RequestBody ProductRequest request){
		return service.addProduct(request.getRequest());
	}
	
	@GetMapping("/{productId}")
	public ResponseEntity<Object> fetchProduct(@PathVariable Long productId){
		return service.fetchProduct(productId);
	}
	
	@PutMapping("/{productId}")
	public ResponseEntity<Object> updateProduct(@PathVariable Long productId, @RequestBody ProductRequest request){
		return service.updateProduct(productId, request.getRequest());
	}
	
	@DeleteMapping("/{productId}")
	public ResponseEntity<Object> deleteProduct(@PathVariable Long productId){
		return service.deleteProduct(productId);
	}
	
	@GetMapping("/order-product/{id}/price")
    public double getProductPrice(@PathVariable Long id) {
		return service.getProductPrice(id);
	}
	
	@PutMapping("/{productId}/{stock}")
    public int updateProductStock(@PathVariable Long productId, @PathVariable Integer stock){
		return service.updateProductStock(productId, stock);
	}
}
