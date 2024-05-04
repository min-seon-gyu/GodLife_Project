package com.example.web.product;

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
        this.price = convert(product.getPrice());
        this.quantity = convert(product.getQuantity());
    }

    private String convert(Long number){
        StringBuilder sb = new StringBuilder();
        while(number >= 10){
            sb.append(number%10);
            number /= 10;
            if(sb.length() % 4 == 3){
                sb.append(",");
            }
        }
        sb.append(number);
        return sb.reverse().toString();
    }
}
