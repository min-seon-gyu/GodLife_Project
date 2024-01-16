package com.example.web.member;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
public class MemberFindPasswordDto {
    private String loginId;
    private String name;
    private String email;
}
