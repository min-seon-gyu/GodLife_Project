package com.example.web.schedule;

import lombok.Data;

@Data
public class ScheduleResponse {
    private Long id;
    private String content;
    private String status;
    public ScheduleResponse(Schedule schedule){
        this.id = schedule.getId();
        this.content = schedule.getContent();
        this.status = schedule.getStatus().getName();
    }
}
