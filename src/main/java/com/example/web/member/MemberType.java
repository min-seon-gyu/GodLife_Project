package com.example.web.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberType {
    BASIC("일반"),
    OAUTH("OAuth");
    private final String name;
}
