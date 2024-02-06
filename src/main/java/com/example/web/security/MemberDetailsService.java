package com.example.web.security;

import com.example.web.exception.ErrorCode;
import com.example.web.exception.RestApiException;
import com.example.web.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// login 요청이 오면 자동으로 UserDetailsService 타입의 빈의 loadUserByUsername 함수가 실행
@Service
@RequiredArgsConstructor
public class MemberDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;
    //여기서 리턴되는 값이 Authentication 내부로 들어간다.
    @Override
    public MemberDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        System.out.println("loadUserByUsername");
        return memberRepository.findTop1ByLoginId(loginId).map(m -> new MemberDetails(m)).orElseThrow(() -> new RestApiException(ErrorCode.BAD_REQUEST, "해당 유저를 찾을 수 없습니다."));
    }
}
