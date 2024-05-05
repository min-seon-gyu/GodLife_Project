package com.example.web.item;

import lombok.Data;

@Data
public class ItemResponse {
    private Long id;
    private String name;
    private Long quantity;

    public ItemResponse(Item item){
        this.id = item.getId();
        this.quantity = item.getQuantity();
        this.name = item.getProduct().getName();
    }
}
