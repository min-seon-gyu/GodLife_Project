package com.example.web.es;


import lombok.Data;
import java.time.LocalDate;

@Data
public class ScheduleDocumentResponse {
    private LocalDate localDate;
    private String content;
    private boolean status;

    public ScheduleDocumentResponse(ScheduleDocument scheduleDocument){
        this.content = scheduleDocument.getContent();
        this.localDate = scheduleDocument.getLocalDate();
        this.status = scheduleDocument.getStatus();
    }
}
