package com.example.web.security;

import lombok.Builder;
import lombok.Data;

@Data
public class MemberSecurityDto {
    private Long id;
    private String username;
    private String password;
    private String name;
    private String email;
    private String role;

    @Builder
    private MemberSecurityDto(Long id, String username, String password, String name, String email, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.role = role;
    }
}
