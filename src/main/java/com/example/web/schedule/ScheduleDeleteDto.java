package com.example.web.schedule;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ScheduleDeleteDto {
    @NotNull(message = "ID를 입력해주세요")
    private Long id;
    @NotBlank(message = "날짜를 입력해주세요")
    private String date;
}
