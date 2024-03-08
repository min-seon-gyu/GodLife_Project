package com.example.web.schedule;

import com.example.web.common.JpaBaseEntity;
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
    private String username;
    private String content;
    private boolean status;
    @Temporal(TemporalType.DATE)
    private LocalDate localDate;

    @Builder
    public Schedule(String username, String content, LocalDate localDate) {
        this.username = username;
        this.content = content;
        this.localDate = localDate;
    }

    public void changeStatus(){
        this.status = !this.status;
    }

    public void setStatus(boolean status){
        this.status = status;
    }

    public void changeContent(String content){
        this.content = content;
    }
}
