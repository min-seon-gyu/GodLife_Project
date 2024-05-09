package com.example.web.product;

import com.example.web.common.JpaBaseEntity;
import com.example.web.exception.ErrorCode;
import com.example.web.exception.RestApiException;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@DynamicUpdate
@ToString
public class Product extends JpaBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_ID")
    private Long id;
    private String name;
    private Long price;
    private Long quantity;

    @Builder
    public Product(String name, Long quantity, Long price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public void update(ProductUpdateDto productUpdateDto){
        this.name = productUpdateDto.getName();
        this.quantity = productUpdateDto.getQuantity();
        this.price = productUpdateDto.getPrice();
    }

    public void increaseQuantity(Long quantity) {
        this.quantity += quantity;
    }

    public void decreaseQuantity(Long quantity){
        Long restQuantity = this.quantity - quantity;
        if(restQuantity < 0) throw new RestApiException(ErrorCode.BAD_REQUEST, "상품 수량이 부족합니다.");
        this.quantity = restQuantity;
    }
}
