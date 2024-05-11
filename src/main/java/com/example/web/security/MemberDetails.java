package com.example.web.security;

import com.example.web.member.MemberController;
import com.example.web.member.MemberType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;


public class MemberDetails implements UserDetails, OAuth2User {

    private final MemberSecurityDto member;
    private final MemberType type;
    private Map<String, Object> attributes;

    //일반 로그인
    public MemberDetails(MemberSecurityDto member) {
        type = MemberType.BASIC;
        this.member = member;
    }

    //OAuth 로그인
    public MemberDetails(MemberSecurityDto member, Map<String, Object> attributes) {
        type = MemberType.OAUTH;
        this.member = member;
        this.attributes = attributes;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public boolean isBasic() { return type.equals(MemberType.BASIC);}

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
    public Long getId() { return member.getId(); }
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
