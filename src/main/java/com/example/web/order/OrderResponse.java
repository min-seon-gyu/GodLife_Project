package com.example.web.order;

import com.example.web.common.NumberFormatConvert;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderResponse {
    private Long id;
    private String name;
    private LocalDateTime orderDate;
    private String quantity;
    private String price;
    private Long memberId;
    private Long productId;

    public OrderResponse(Order order){
        this.id = order.getId();
        this.name = order.getProduct().getName();
        this.orderDate = order.getLastModifiedDate();
        this.quantity = NumberFormatConvert.convert(order.getTotalQuantity());
        this.price = NumberFormatConvert.convert(order.getTotalPrice());
        this.memberId = order.getMember().getId();
        this.productId = order.getProduct().getId();
    }
}
