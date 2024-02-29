package com.example.web.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;


public class MemberDetails implements UserDetails, OAuth2User {

    private final MemberSecurityDto member;
    private final boolean isOAuth2;
    private Map<String, Object> attributes;

    //일반 로그인
    public MemberDetails(MemberSecurityDto member) {
        isOAuth2 = false;
        this.member = member;
    }

    //OAuth 로그인
    public MemberDetails(MemberSecurityDto member, Map<String, Object> attributes) {
        isOAuth2 = true;
        this.member = member;
        this.attributes = attributes;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public boolean isOAuth2User() { return isOAuth2;}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collections = new ArrayList<>();
        collections.add(() -> member.getRole());
        return collections;
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getUsername();
    }

    @Override
    public String getName() {
        return member.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
