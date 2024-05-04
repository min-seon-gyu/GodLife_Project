package com.example.web.member;

import com.example.web.exception.ErrorCode;
import com.example.web.exception.RestApiException;
import com.example.web.security.PasswordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordService passwordService;
    private final String SIGN_KEY = "SIGN_KEY_";

    public String signUp(MemberSignUpDto memberSignUpDto){
        if(validationCheck(memberSignUpDto)){
            throw new RestApiException(ErrorCode.BAD_REQUEST, "이미 가입된 회원정보입니다.");
        }
        return SIGN_KEY + memberSignUpDto.getEmail() + "_" + memberSignUpDto.getEmail().hashCode();
    }

    @Transactional
    public Long signUpConfirm(Map<Object, Object> data){
        Member member = Member.builder()
                .username((String) data.get("username"))
                .email((String) data.get("email"))
                .name((String) data.get("name"))
                .password(passwordService.encode((String) data.get("password")))
                .role(MemberRole.USER)
                .isOAuth(false)
                .point(0l)
                .build();
        return memberRepository.save(member).getId();
    }

    @Transactional
    public Long update(MemberUpdateDto memberUpdateDto, Long id) {
        Optional<Member> findMember = memberRepository.findById(id);
        Member member = findMember.orElseThrow(() -> new RestApiException(ErrorCode.BAD_REQUEST, "해당하는 회원이 없습니다."));
        member.update(memberUpdateDto);
        return member.getId();
    }

    @Transactional
    public Long updatePassword(MemberUpdatePasswordDto memberUpdatePasswordDto, Long id){
        Optional<Member> findMember = memberRepository.findById(id);
        Member member = findMember.orElseThrow(() -> new RestApiException(ErrorCode.BAD_REQUEST, "해당하는 회원이 없습니다."));
        member.changePassword(passwordService.encode(memberUpdatePasswordDto.getPassword()));
        return member.getId();
    }

    @Transactional
    public void delete(Long id) {
        Optional<Member> findMember = memberRepository.findById(id);
        memberRepository.delete(findMember.orElseThrow(() -> new RestApiException(ErrorCode.BAD_REQUEST, "해당하는 회원이 없습니다.")));
    }

    @Transactional
    public String findPassword(MemberFindPasswordDto memberFindPasswordDto) {
        log.info("비밀번호 찾기 요청 - id : {}, name : {}, email : {}", memberFindPasswordDto.getUsername(), memberFindPasswordDto.getName(), memberFindPasswordDto.getEmail());
        Optional<Member> findMember = memberRepository.findTop1ByUsernameAndNameAndEmailAndIsOAuth(memberFindPasswordDto.getUsername(), memberFindPasswordDto.getName(), memberFindPasswordDto.getEmail(), false);
        Member member = findMember.orElseThrow(() -> new RestApiException(ErrorCode.BAD_REQUEST, "해당하는 회원이 없습니다."));
        String password = passwordService.getRandom();
        member.changePassword(passwordService.encode(password));

        return password;
    }


    private boolean validationCheck(MemberSignUpDto memberSignUpDto){
        return memberRepository.existsByUsername(memberSignUpDto.getUsername()) || memberRepository.existsByEmailAndIsOAuth(memberSignUpDto.getEmail(), false);
    }
}
