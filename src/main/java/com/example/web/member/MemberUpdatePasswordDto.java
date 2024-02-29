package com.example.web.member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MemberUpdatePasswordDto {
    @NotBlank(message = "비밀번호를 입력해주세요")
    @Size(min = 8, max = 12, message = "비밀번호는 8자 이상 12자 이하여야합니다.")
    private String password;
    @NotBlank(message = "비밀번호 확인을 입력해주세요")
    @Size(min = 8, max = 12, message = "비밀번호 확인은 8자 이상 12자 이하여야합니다.")
    private String passwordCheck;

    public boolean validPassword(){
        return this.password.equals(this.passwordCheck);
    }
}
