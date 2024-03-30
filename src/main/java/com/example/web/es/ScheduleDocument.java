package com.example.web.es;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.time.LocalDate;

@Getter
@Document(indexName = "schedule")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class ScheduleDocument {
    @Id
    @Field(name = "schedule_id", type = FieldType.Long)
    private Long scheduleId;
    @Field(name = "member_id", type = FieldType.Long)
    private Long memberId;
    @Field(name = "content", type = FieldType.Text)
    private String content;
    @Field(name = "local_date", type = FieldType.Date)
    @Temporal(TemporalType.DATE)
    private LocalDate localDate;
}
