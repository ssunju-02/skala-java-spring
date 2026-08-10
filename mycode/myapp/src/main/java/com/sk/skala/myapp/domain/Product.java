package com.sk.skala.myapp.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO_INCREMENT
    private Long id;

    @Column(name = "product_name", nullable = false, length = 100)
    private String name; // 상품명 (최대 100자, NOT NULL)

    @Column(nullable = false)
    private Integer price; // 가격 (NOT NULL)

    @Column(name = "stock_quantity", columnDefinition = "INT DEFAULT 0")
    private Integer stockQuantity; // 재고 수량 (기본값 0)

    @Enumerated(EnumType.STRING) // DB에 "ON_SALE" 같은 문자열로 저장
    @Column(nullable = false)
    private ProductStatus status; // 상품 상태 (ON_SALE / SOLD_OUT / DISCONTINUED)

    @Lob
    @Column(columnDefinition = "TEXT")
    private String description; // 상품 상세 설명 (대용량 텍스트)

    @ManyToOne(fetch = FetchType.LAZY) // N:1 관계 - 여러 상품이 한 명의 사용자에 속함
    @JoinColumn(name = "user_id") // products 테이블에 user_id FK 컬럼 생성
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User user; // 이 상품을 등록한 사용자

    @Transient
    private String displayLabel; // "상품명 (상태)" 형태의 표시용 라벨, DB에 저장 안 됨

    /**
     * DB에서 조회한 뒤 displayLabel을 조합해 반환하는 편의 메서드.
     * @Transient 필드는 이처럼 런타임에 계산되는 값에 활용한다.
     */
    public String getDisplayLabel() {
        return name + " (" + (status != null ? status.name() : "N/A") + ")";
    }
}
