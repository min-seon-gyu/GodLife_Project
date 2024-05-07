package com.example.web.item;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ItemUseDto {
    @NotBlank(message = "ID를 입력해주세요")
    private Long id;
}
