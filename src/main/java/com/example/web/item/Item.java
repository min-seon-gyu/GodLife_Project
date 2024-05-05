package com.example.web.item;

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
@Getter
@DynamicUpdate
public class Item extends JpaBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ITEM_ID")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;
    private Long quantity;

    @Builder
    public Item(Product product, Member member, Long quantity) {
        this.product = product;
        this.member = member;
        this.quantity = quantity;
    }

    public void setMember(Member member){
        this.member = member;
    }

    public void addQuantity(Long quantity){
        this.quantity += quantity;
    }
}
