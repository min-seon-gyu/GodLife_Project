package com.example.web.product;

import com.example.web.common.NumberFormatConvert;
import lombok.Data;

@Data
public class ProductResponse {
    private Long id;
    private String name;
    private String price;
    private String quantity;

    public ProductResponse(Product product){
        this.id = product.getId();
        this.name = product.getName();
        this.price = NumberFormatConvert.convert(product.getPrice());
        this.quantity = NumberFormatConvert.convert(product.getQuantity());
    }
}
