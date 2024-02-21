package com.example.web.member;

import com.example.web.exception.ErrorCode;
import com.example.web.exception.RestApiException;
import com.example.web.security.PasswordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {
    private enum EmailType {
        ID, PASSWORD
    }
    private final MemberRepository memberRepository;
    private final PasswordService passwordService;
    private final JavaMailSender javaMailSender;

    @Transactional
    public void signUp(MemberSignUpDto memberSignUpDto){
        log.info("회원가입 요청 - id : {}, name : {}, email : {}", memberSignUpDto.getUsername(), memberSignUpDto.getName(), memberSignUpDto.getEmail());
        if(validationCheck(memberSignUpDto)){
            log.info("회원가입 요청 실패- id : {}, name : {}, email : {}, {}", memberSignUpDto.getUsername(), memberSignUpDto.getName(), memberSignUpDto.getEmail(), "이미 회원가입 했습니다.");
            throw new RestApiException(ErrorCode.BAD_REQUEST, "이미 가입된 회원정보입니다.");
        }
        Member member = Member.builder()
                .username(memberSignUpDto.getUsername())
                .email(memberSignUpDto.getEmail())
                .name(memberSignUpDto.getName())
                .password(passwordService.encode(memberSignUpDto.getPassword()))
                .role(MemberRole.USER)
                .build();

        memberRepository.save(member);
    }

    public void update(MemberUpdateDto memberUpdateDto) {
    }

    @Transactional
    public void delete() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<Member> findMember = memberRepository.findTop1ByUsername((String) authentication.getPrincipal());
        memberRepository.delete(findMember.get());
    }

    public void findLoginId(MemberFindUsernameDto memberFindUsernameDto){
        log.info("아이디 찾기 요청 - name : {}, email : {}", memberFindUsernameDto.getName(), memberFindUsernameDto.getEmail());
        Optional<Member> member = memberRepository.findTop1ByNameAndEmail(memberFindUsernameDto.getName(), memberFindUsernameDto.getEmail());
        if(member.isEmpty()){
            log.info("아이디 찾기 요청 실패 - name : {}, email : {}, {}", memberFindUsernameDto.getName(), memberFindUsernameDto.getEmail(), "해당하는 회원이 없습니다.");
            throw new RestApiException(ErrorCode.BAD_REQUEST, "해당하는 회원이 없습니다.");
        }
        sendMail(EmailType.ID, memberFindUsernameDto.getEmail(), member.get().getUsername());
    }


    @Transactional
    public void findPassword(MemberFindPasswordDto memberFindPasswordDto) {
        log.info("비밀번호 찾기 요청 - id : {}, name : {}, email : {}", memberFindPasswordDto.getUsername(), memberFindPasswordDto.getName(), memberFindPasswordDto.getEmail());
        Optional<Member> member = memberRepository.findTop1ByUsernameAndNameAndEmail(memberFindPasswordDto.getUsername(), memberFindPasswordDto.getName(), memberFindPasswordDto.getEmail());
        if(member.isEmpty()){
            log.info("비밀번호 찾기 요청 실패 - id : {}, name : {}, email : {}. {}", memberFindPasswordDto.getUsername(), memberFindPasswordDto.getName(), memberFindPasswordDto.getEmail(), "해당하는 회원이 없습니다.");
            throw new RestApiException(ErrorCode.BAD_REQUEST, "해당하는 회원이 없습니다.");
        }
        String password = passwordService.getRandomPassword();
        Member findMember = memberRepository.findTop1ByUsername(memberFindPasswordDto.getUsername()).get();
        findMember.changePassword(passwordService.encode(password));
        sendMail(EmailType.PASSWORD, memberFindPasswordDto.getEmail(), password);
    }


    private boolean validationCheck(MemberSignUpDto memberSignUpDto){
        return memberRepository.existsByUsername(memberSignUpDto.getUsername()) || memberRepository.existsByEmail(memberSignUpDto.getEmail());
    }

    private void sendMail(EmailType emailType, String email, String content){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        switch (emailType) {
            case ID -> {
                message.setSubject("통합 예약 사이트 아이디 찾기");
                message.setText("아이디는 " + content + "입니다.");
            }
            case PASSWORD -> {
                message.setSubject("통합 예약 사이트 비밀번호 찾기");
                message.setText("임시 비밀번호는 " + content + "입니다.");
            }
        }
        javaMailSender.send(message);
    }
}
