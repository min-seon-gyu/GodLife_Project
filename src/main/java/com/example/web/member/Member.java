package com.example.web.member;

import com.example.web.common.JpaBaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member extends JpaBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(unique = true)
    private String loginId;
    private String password;
    private String name;
    @Column(unique = true)
    private String email;
    private String address;

    @Builder(access = AccessLevel.PROTECTED)
    private Member(String loginId, String password, String name, String email, String address){
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.email = email;
        this.address = address;
    }

    public void changePassword(String password){
        this.password = password;
    }
}
