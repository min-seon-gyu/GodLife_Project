package com.example.web.security;

import com.example.web.exception.ErrorCode;
import com.example.web.exception.RestApiException;
import com.example.web.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class JwtMemberDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;
    @Override
    public JwtMemberDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        return memberRepository.findTop1ByLoginId(loginId).map(m -> new JwtMemberDetails(m, Collections.singleton(new SimpleGrantedAuthority(m.getRole().name())))).orElseThrow(() -> new RestApiException(ErrorCode.BAD_REQUEST, "해당 유저를 찾을 수 없습니다."));
    }
}
