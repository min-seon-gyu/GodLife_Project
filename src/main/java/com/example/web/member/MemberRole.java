package com.example.web.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberRole {
    USER("일반"),
    ADMIN("관리자");
    private final String name;
}
