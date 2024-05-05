package com.example.web.order;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderCalculatorDto {
    @NotNull(message = "가격을 입력해주세요")
    private Long price;
    @NotNull(message = "수량을 입력해주세요")
    private Long quantity;
    @NotNull(message = "쿠폰 ID를 입력해주세요")
    private Long userCouponId;
}
