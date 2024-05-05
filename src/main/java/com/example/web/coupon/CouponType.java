package com.example.web.coupon;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CouponType {
    FIXED("고정"),
    RATE("비율");
    private final String type;
}