package com.example.web.member;

import com.example.web.security.JwtProvider;
import com.example.web.security.MemberRole;
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
        log.info("회원가입 요청 - id : {}, name : {}, email : {}", memberSignUpDto.getLoginId(), memberSignUpDto.getName(), memberSignUpDto.getEmail());
        if(validationCheck(memberSignUpDto)){
            log.info("회원가입 요청 실패- id : {}, name : {}, email : {}, {}", memberSignUpDto.getLoginId(), memberSignUpDto.getName(), memberSignUpDto.getEmail(), "이미 회원가입 했습니다.");
            throw new IllegalStateException();
        }
        Member member = Member.builder()
                .loginId(memberSignUpDto.getLoginId())
                .email(memberSignUpDto.getEmail())
                .name(memberSignUpDto.getName())
                .address(memberSignUpDto.getAddress())
                .password(passwordService.encryption(memberSignUpDto.getPassword()))
                .role(MemberRole.USER)
                .build();

        memberRepository.save(member);
    }

    public String login(MemberLoginDto memberLoginDto) {
        log.info("로그인 요청 - id : {}", memberLoginDto.getLoginId());
        Optional<Member> findMember = memberRepository.findTop1ByLoginId(memberLoginDto.getLoginId());
        if (findMember.isEmpty()) {
            log.info("로그인 요청 실패- id : {}", memberLoginDto.getLoginId());
            throw new IllegalStateException();
        }
        Member member = findMember.get();
        if (!passwordService.match(memberLoginDto.getPassword(), member.getPassword())) {
            log.info("로그인 요청 실패- id : {}", memberLoginDto.getLoginId());
            throw new IllegalStateException();
        }
        return JwtProvider.generateJwtToken(member);
    }

    public void logout() {
    }

    @Transactional
    public void update(MemberUpdateDto memberUpdateDto) {
        log.info("업데이트 요청 - address : {}", memberUpdateDto.getAddress());
    }

    @Transactional
    public void delete() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<Member> findMember = memberRepository.findTop1ByLoginId((String) authentication.getPrincipal());
        memberRepository.delete(findMember.get());
    }

    public void findLoginId(MemberFindLoginIdDto memberFindLoginIdDto){
        log.info("아이디 찾기 요청 - name : {}, email : {}", memberFindLoginIdDto.getName(), memberFindLoginIdDto.getEmail());
        Optional<Member> member = memberRepository.findTop1ByNameAndEmail(memberFindLoginIdDto.getName(), memberFindLoginIdDto.getEmail());
        if(member.isEmpty()){
            log.info("아이디 찾기 요청 실패 - name : {}, email : {}, {}", memberFindLoginIdDto.getName(), memberFindLoginIdDto.getEmail(), "해당하는 회원이 없습니다.");
            throw new IllegalStateException();
        }
        sendMail(EmailType.ID, memberFindLoginIdDto.getEmail(), member.get().getLoginId());
    }


    @Transactional
    public void findPassword(MemberFindPasswordDto memberFindPasswordDto) {
        log.info("비밀번호 찾기 요청 - id : {}, name : {}, email : {}", memberFindPasswordDto.getLoginId(), memberFindPasswordDto.getName(), memberFindPasswordDto.getEmail());
        Optional<Member> member = memberRepository.findTop1ByLoginIdAndNameAndEmail(memberFindPasswordDto.getLoginId(), memberFindPasswordDto.getName(), memberFindPasswordDto.getEmail());
        if(member.isEmpty()){
            log.info("비밀번호 찾기 요청 실패 - id : {}, name : {}, email : {}. {}", memberFindPasswordDto.getLoginId(), memberFindPasswordDto.getName(), memberFindPasswordDto.getEmail(), "해당하는 회원이 없습니다.");
            throw new IllegalStateException();
        }
        String password = passwordService.create();
        Member findMember = memberRepository.findTop1ByLoginId(memberFindPasswordDto.getLoginId()).get();
        findMember.changePassword(passwordService.encryption(password));
        sendMail(EmailType.PASSWORD, memberFindPasswordDto.getEmail(), password);
    }

    @Transactional(readOnly = true)
    private boolean validationCheck(MemberSignUpDto memberSignUpDto){
        return memberRepository.existsByLoginId(memberSignUpDto.getLoginId()) || memberRepository.existsByEmail(memberSignUpDto.getEmail());
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
