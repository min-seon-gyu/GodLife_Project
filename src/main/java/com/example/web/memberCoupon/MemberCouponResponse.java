package com.example.web.memberCoupon;

import lombok.Data;

@Data
public class MemberCouponResponse {
    private Long id;
    private String name;

    public MemberCouponResponse(MemberCoupon memberCoupon){
        this.id = memberCoupon.getId();
        this.name = memberCoupon.getCoupon().getName();
    }
}
