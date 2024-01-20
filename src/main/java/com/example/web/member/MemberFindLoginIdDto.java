package com.example.web.member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MemberFindLoginIdDto {
    private String name;
    private String email;
}
