package com.sk.skala.myapp.repository;

import com.sk.skala.myapp.domain.Product;
import com.sk.skala.myapp.domain.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // 상태별 상품 목록 조회 (쿼리 메서드)
    List<Product> findByStatus(ProductStatus status);
}
