package com.example.web.schedule;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ScheduleStatus {
    INCOMPLETE("미완료"),
    COMPLETE("완료");
    private final String name;
}
