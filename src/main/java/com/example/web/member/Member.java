package com.example.web.member;

import com.example.web.common.JpaBaseEntity;
import com.example.web.exception.ErrorCode;
import com.example.web.exception.RestApiException;
import com.example.web.item.Item;
import com.example.web.memberCoupon.MemberCoupon;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@DynamicUpdate
public class Member extends JpaBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private Long id;
    @OneToMany(mappedBy = "member")
    private List<Item> items = new ArrayList<>();
    @OneToMany(mappedBy = "member")
    private List<MemberCoupon> coupons = new ArrayList<>();
    @Column(unique = true)
    private String username;
    private String password;
    private String name;
    private String email;
    private String address;
    @Enumerated(EnumType.STRING)
    private MemberRole role;
    private String provider;
    private String providerId;
    private boolean isOAuth;
    private Long point;

    @Builder
    private Member(String username, String password, String name, String email, String address, MemberRole role, String provider, String providerId, boolean isOAuth, Long point){
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.address = address;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
        this.isOAuth = isOAuth;
        this.point = point;
    }

    public void changePassword(String password){
        this.password = password;
    }

    public void update(MemberUpdateDto memberUpdateDto){
        this.address = memberUpdateDto.getAddress();
    }

    public void buy(Long point){
        validate(point);
        this.point -= point;
    }

    public void addPoint(){
        this.point += 500;
    }

    public void setItems(Item... items){
        for (Item item : items) {
            this.items.add(item);
            item.setMember(this);
        }
    }

    public void setCoupons(MemberCoupon... coupons){
        for (MemberCoupon coupon : coupons) {
            this.coupons.add(coupon);
            coupon.setMember(this);
        }
    }

    private void validate(Long point) {
        Long restPoint = this.point - point;
        if(restPoint < 0) throw new RestApiException(ErrorCode.BAD_REQUEST, "포인트가 부족합니다.");
    }
}
