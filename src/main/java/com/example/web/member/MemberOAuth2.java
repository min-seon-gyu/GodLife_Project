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
@Table(name = "member_oauth2")
public class MemberOAuth2 extends JpaBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_OAUTH2_ID")
    private Long id;
    private String username;
    private String password;
    private String name;
    private String email;
    private String address;
    @Enumerated(EnumType.STRING)
    private MemberRole role;
    private String provider;
    private String providerId;

    @Builder
    private MemberOAuth2(String username, String password, String name, String email, MemberRole role, String provider, String providerId){
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
    }

    public void update(MemberUpdateDto memberUpdateDto){
        this.address = memberUpdateDto.getAddress();
    }
}
