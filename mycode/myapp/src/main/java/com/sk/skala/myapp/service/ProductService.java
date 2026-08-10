package com.sk.skala.myapp.service;

import com.sk.skala.myapp.domain.Product;
import com.sk.skala.myapp.domain.ProductStatus;
import com.sk.skala.myapp.repository.ProductRepository;
import com.sk.skala.myapp.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

// 클래스 레벨 readOnly=true: 조회 메소드는 변경 감지/추적을 하지 않아 성능이 좋고, 쓰기가 원천 차단됨
@Service
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public ProductService(ProductRepository productRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    // 전체 상품 목록 조회
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // 상품 단건 조회
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    // 상태별 상품 목록 조회
    public List<Product> getProductsByStatus(ProductStatus status) {
        return productRepository.findByStatus(status);
    }

    // userId 기반으로 상품 목록 검색
    public List<Product> getProductsByUserId(Long userId) {
        return productRepository.findByUserId(userId);
    }

    // 사용자 이름으로 상품 목록 조회
    public List<Product> getProductsByUserName(String userName) {
        return productRepository.findByUserName(userName);
    }

    // 신규 상품 등록 (userId로 등록한 사용자를 연결)
    @Transactional
    public Product createProduct(Product product, Long userId) {
        if (userId != null) {
            userRepository.findById(userId).ifPresent(product::setUser);
        }
        return productRepository.save(product);
    }

    // 기존 상품 정보 수정
    @Transactional
    public Optional<Product> updateProduct(Long id, Product updated, Long userId) {
        return productRepository.findById(id).map(product -> {
            product.setName(updated.getName());
            product.setPrice(updated.getPrice());
            product.setStockQuantity(updated.getStockQuantity());
            product.setStatus(updated.getStatus());
            product.setDescription(updated.getDescription());
            if (userId != null) {
                userRepository.findById(userId).ifPresent(product::setUser);
            }
            return productRepository.save(product);
        });
    }

    // 상품 삭제
    @Transactional
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
