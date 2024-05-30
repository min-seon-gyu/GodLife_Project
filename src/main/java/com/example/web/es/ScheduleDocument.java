package com.example.web.es;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.*;
import org.springframework.data.annotation.Id;
import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class ScheduleDocument {
    @Id
    @JsonSetter("schedule_id")
    private Long scheduleId;
    @JsonSetter("member_id")
    private Long memberId;
    @JsonSetter("content")
    private String content;
    @JsonSetter("status")
    private String status;
    @JsonSetter("local_date")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @Temporal(TemporalType.DATE)
    private LocalDate localDate;
}
