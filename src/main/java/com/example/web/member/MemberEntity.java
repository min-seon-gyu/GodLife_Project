package com.example.web.member;

import com.example.web.memberReservation.MemberReservationEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
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

    @OneToMany(mappedBy = "memberEntity")
    List<MemberReservationEntity> memberReservations = new ArrayList<>();

    protected MemberEntity(String id, String password, String name, String email){
        this.id = id;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public static MemberEntity CreateMemberEntity(String id, String password, String name, String email){
        return new MemberEntity(id, password, name, email);
    }
}
