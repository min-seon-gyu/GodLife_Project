package com.example.web.order;

import com.example.web.common.JpaBaseEntity;
import com.example.web.member.Member;
import com.example.web.product.Product;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders")
@Getter
@DynamicUpdate
public class Order extends JpaBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_ID")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;
    private Long userCouponId;
    private Long totalPrice;
    private Long totalQuantity;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Builder
    public Order(Member member, Long userCouponId, Product product, Long totalPrice, Long totalQuantity) {
        this.member = member;
        this.product = product;
        this.userCouponId = userCouponId;
        this.totalPrice = totalPrice;
        this.totalQuantity = totalQuantity;
        this.status = OrderStatus.ORDER;
    }
    public void refund(){
        this.status = OrderStatus.REFUND;
        this.product.addQuantity(totalQuantity);
    }
}
