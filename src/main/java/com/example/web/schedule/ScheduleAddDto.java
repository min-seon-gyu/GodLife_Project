package com.example.web.schedule;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ScheduleAddDto {
    @NotBlank(message = "일정을 입력해주세요")
    @Size(min = 2, max = 30, message = "내용은 2자 이상 30자 이하여야합니다.")
    private String content;
    private String date;
}
