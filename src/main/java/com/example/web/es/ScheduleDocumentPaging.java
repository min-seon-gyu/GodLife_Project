package com.example.web.es;

import lombok.Data;
import java.util.List;

@Data
public class ScheduleDocumentPaging {
    private List<ScheduleDocumentResponse> lst;
    private boolean hasNext;
    public ScheduleDocumentPaging(List<ScheduleDocumentResponse> lst){
        this.lst = lst;
    }
}
