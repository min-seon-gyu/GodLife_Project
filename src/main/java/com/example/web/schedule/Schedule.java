package com.example.web.schedule;

import com.example.web.common.JpaBaseEntity;
import com.example.web.member.Member;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
public class Schedule extends JpaBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SCHEDULE_ID")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;
    private String content;
    private ScheduleStatus status = ScheduleStatus.INCOMPLETE;
    @Temporal(TemporalType.DATE)
    private LocalDate localDate;

    @Builder
    public Schedule(Member member, String content, LocalDate localDate) {
        this.member = member;
        this.content = content;
        this.localDate = localDate;
    }

    public void success(){
        this.member.addPoint();
        this.status = ScheduleStatus.COMPLETE;
    }

    public void initStatus(){
        this.status = ScheduleStatus.INCOMPLETE;
    }

    public void changeContent(String content){
        this.content = content;
    }
}
