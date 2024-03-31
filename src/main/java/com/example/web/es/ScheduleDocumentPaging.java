package com.example.web.es;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
public class ScheduleDocumentPaging {
    private List<ScheduleDocumentResponse> lst;
    private boolean hasNext;
    private int nextIndex;
    @Builder
    public ScheduleDocumentPaging(List<ScheduleDocumentResponse> lst, boolean hasNext, int nextIndex) {
        this.lst = lst;
        this.hasNext = hasNext;
        this.nextIndex = nextIndex;
    }
}
