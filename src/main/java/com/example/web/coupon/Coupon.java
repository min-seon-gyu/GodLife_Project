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
    private CouponType type;

    @Builder
    public Coupon(String name, Long quantity, Long discountPrice, Long discountRate, CouponType type) {
        this.name = name;
        this.discountPrice = discountPrice;
        this.discountRate = discountRate;
        this.quantity = quantity;
        this.type = type;
    }
}
