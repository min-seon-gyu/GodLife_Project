package com.example.web.order;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderAddDto {
    @NotNull(message = "상품 ID를 입력해주세요")
    private Long productId;
    @NotNull(message = "쿠폰 ID를 입력해주세요")
    private Long userCouponId;
    @NotNull(message = "수량을 입력해주세요")
    private Long totalQuantity;
    @NotNull(message = "금액을 입력해주세요")
    private Long totalPrice;
}
