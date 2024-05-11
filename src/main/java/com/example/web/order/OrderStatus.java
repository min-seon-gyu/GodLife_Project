package com.example.web.order;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {
    ORDER("구매"),
    REFUND("환불");

    private final String name;
}
