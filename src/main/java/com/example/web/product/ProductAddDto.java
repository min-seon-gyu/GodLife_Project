package com.example.web.product;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ProductAddDto {
    @NotBlank(message = "이름을 입력해주세요")
    @Size(min = 2, max = 30, message = "이름은 2자 이상 30자 이하여야합니다.")
    private String name;
    @NotNull(message = "가격을 입력해주세요")
    @Min(value = 100)
    @Max(value = 100000)
    private Long price;
    @NotNull(message = "수량을 입력해주세요")
    @Min(value = 1)
    @Max(value = 10000)
    private Long quantity;
}
