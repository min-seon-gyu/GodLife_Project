package com.example.web.memberCoupon;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberCouponStatus {
    UNUSED("사용되지 않음"),
    USED("사용됨");

    private final String name;
}
