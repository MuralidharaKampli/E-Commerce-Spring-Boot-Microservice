package com.mss.product_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mss.product_service.entity.Product;
import com.mss.product_service.entity.Request;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface ProductRepository extends JpaRepository<Request, Long> {
	@Query("select p from Product p where p.productId = :productId")
	public Optional<Product> findByProductId(@Param("productId") Long productId);

	@Query(value = "SELECT r.* FROM M_MS_PRODUCT p JOIN M_MS_PRODUCT_REQUEST r ON p.rid = r.request_id WHERE p.product_id = :productId", nativeQuery = true)
	Optional<Request> findRequestByProductId(@Param("productId") Long productId);
}
