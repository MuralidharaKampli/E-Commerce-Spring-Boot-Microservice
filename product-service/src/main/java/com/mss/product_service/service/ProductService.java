package com.mss.product_service.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mss.product_service.dto.Message;
import com.mss.product_service.dto.ProductDTO;
import com.mss.product_service.dto.Response;
import com.mss.product_service.dto.ResponseDTO;
import com.mss.product_service.entity.Product;
import com.mss.product_service.entity.Request;
import com.mss.product_service.repository.ProductRepository;

@Service
public class ProductService {
	@Autowired
	private ProductRepository repository;

	@Autowired
	private ModelMapper mapper;

	public ResponseEntity<Object> addProduct(Request request) {
		Request productSave = repository.save(request);
		ResponseDTO dto = new ResponseDTO();
		Response map = mapper.map(productSave, Response.class);
		map.setMessage("Product stored successfully");
		dto.setResponse(map);
		return ResponseEntity.status(HttpStatus.OK).body(dto);
	}

	public ResponseEntity<Object> fetchProduct(Long productId) {
		Optional<Product> productFound = repository.findByProductId(productId);
		ResponseDTO dto = new ResponseDTO();
		Response response = new Response();
		if (productFound.isPresent()) {
			Product product = productFound.get();
			ProductDTO map = mapper.map(product, ProductDTO.class);
			response.setMessage("Product fetched successfully");
			response.setProduct(map);
			dto.setResponse(response);
			return ResponseEntity.status(HttpStatus.OK).body(dto);
		} else {
			response.setMessage("Product fetch unsuccessfully, product id not exists");
			dto.setResponse(response);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(dto);
		}
	}

	public ResponseEntity<Object> updateProduct(Long productId, Request request) {
		Optional<Request> requestFound = repository.findRequestByProductId(productId);
		Message message = new Message();
		if (requestFound.isPresent()) {
			Request request2 = requestFound.get();
			request2.getProduct().setName(request.getProduct().getName());
			request2.getProduct().setDescription(request.getProduct().getDescription());
			request2.getProduct().setPrice(request.getProduct().getPrice());
			request2.getProduct().setStock(request.getProduct().getStock());
			request2.getProduct().setCategory(request.getProduct().getCategory());
			request2.getProduct().setRating(request.getProduct().getRating());
			repository.save(request2);
			message.setMessage("Product updated successfully");
			return ResponseEntity.status(HttpStatus.OK).body(message);
		} else {
			message.setMessage("Product update unsuccessfully, product id not exists");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
		}
	}

	public ResponseEntity<Object> deleteProduct(Long productId) {
		Optional<Request> requestFound = repository.findRequestByProductId(productId);
		Message message = new Message();
		if (requestFound.isPresent()) {
			Request request = requestFound.get();
			repository.deleteById(request.getRequestId());
			message.setMessage("Product deleted successfully");
			return ResponseEntity.status(HttpStatus.OK).body(message);
		} else {
			message.setMessage("Product delete unsuccessfully, product id not exists");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
		}
	}

	public double getProductPrice(Long id) {
		Optional<Product> productFound = repository.findByProductId(id);
		if (productFound.isPresent()) {
			return productFound.get().getPrice();
		} else {
			return 0;
		}
	}

	public int updateProductStock(Long productId, Integer stock) {
		Optional<Request> requestFound = repository.findRequestByProductId(productId);
		Request request = requestFound.get();
		int availableStock = request.getProduct().getStock();
		if (requestFound.isPresent() && stock <= availableStock) {
			int totalStock = availableStock - stock;
			request.getProduct().setStock(totalStock);
			repository.save(request);
			return totalStock;
		} else if(availableStock == 0){
			return -1;
		}else {
			return 0;
		}
	}
}
