package com.mss.order_service.exception;

public class ProductStockExceedException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public ProductStockExceedException(String message) {
        super(message);
    }
}
