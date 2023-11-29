package com.example.web.member;

import com.example.web.memberReservation.MemberReservationEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MemberEntity {

    @Id
    @Column(name = "MEMBER_ID")
    private String id;
    @Column(name = "PASSWORD")
    private String password;
    @Column(name = "NAME")
    private String name;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "DATE")
    private Date date;
    @OneToMany(mappedBy = "memberEntity")
    List<MemberReservationEntity> memberReservations = new ArrayList<>();

    protected MemberEntity(String password, String name, String email, Date date){
        this.password = password;
        this.name = name;
        this.email = email;
        this.date = date;
    }

    public static MemberEntity CreateMemberEntity(String password, String name, String email, Date date){
        return new MemberEntity(password, name, email, date);
    }
}
