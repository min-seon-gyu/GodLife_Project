package com.example.web.coupon;

import com.example.web.common.JpaBaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@DynamicUpdate
public class Coupon extends JpaBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COUPON_ID")
    private Long id;
    private String name;
    private Long discountPrice;
    private Long discountRate;
    private Long quantity;
    @Enumerated(EnumType.STRING)
    private CouponType couponType;

    @Builder
    public Coupon(String name, Long value, Long quantity, Long discountPrice, Long discountRate, CouponType couponType) {
        this.name = name;
        this.discountPrice = discountPrice;
        this.discountRate = discountRate;
        this.quantity = quantity;
        this.couponType = couponType;
    }

    public Long costAfterDiscount(Long cost){
        if(couponType.equals(CouponType.FIXED)){
            return cost - discountPrice >= 0 ? cost - discountPrice : 0;
        }

        if(couponType.equals(CouponType.RATE)){
            Long percent = 100l - discountRate;
            return cost * percent / 100;
        }

        throw new IllegalStateException("할인 정책이 존재하지 않습니다");
    }
}
