package com.example.web.security;

import com.example.web.exception.ErrorCode;
import com.example.web.exception.RestApiException;
import com.example.web.member.Member;
import com.example.web.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> findMember = memberRepository.findTop1ByUsername(username);
        if(findMember.isPresent()){
            Member member = findMember.get();
            MemberSecurityDto memberSecurityDto = MemberSecurityDto.builder().username(member.getUsername())
                    .email(member.getEmail())
                    .password(member.getPassword())
                    .name(member.getName())
                    .role(member.getRole().name())
                    .build();
            return new MemberDetails(memberSecurityDto);
        }
        throw new RestApiException(ErrorCode.BAD_REQUEST, "해당 유저를 찾을 수 없습니다.");
    }
};
