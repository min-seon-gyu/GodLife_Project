package com.example.web.member;

import com.example.web.common.JpaBaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@DynamicUpdate
public class Member extends JpaBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private Long id;
    @Column(unique = true)
    private String username;
    private String password;
    private String name;
    private String email;
    private String address;
    @Enumerated(EnumType.STRING)
    private MemberRole role;
    private String provider;
    private String providerId;
    private boolean isOAuth;

    @Builder
    private Member(String username, String password, String name, String email, String address, MemberRole role, String provider, String providerId, boolean isOAuth){
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.address = address;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
        this.isOAuth = isOAuth;
    }

    public void changePassword(String password){
        this.password = password;
    }

    public void update(MemberUpdateDto memberUpdateDto){
        this.address = memberUpdateDto.getAddress();
    }
}
