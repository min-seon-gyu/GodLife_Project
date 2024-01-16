package com.example.web.member;

import lombok.Data;

@Data
public class MemberSignUpDto {
    private String loginId;
    private String password;
    private String name;
    private String email;
    private String address;
}
