package com.example.web.member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
public class MemberFindPasswordDto {
    @NotBlank(message = "아이디를 입력해주세요")
    @Size(min = 8, max = 12, message = "아이디는 8자 이상 12자 이하여야합니다.")
    private String loginId;
    @NotBlank(message = "이름을 입력해주세요")
    private String name;
    @NotBlank(message = "이메일을 입력해주세요")
    @Email(message = "이메일 형식에 맞게 입력해주세요.")
    private String email;
}
