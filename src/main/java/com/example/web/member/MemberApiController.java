package com.example.web.member;

import com.example.web.common.MailService;
import com.example.web.exception.ErrorCode;
import com.example.web.exception.RestApiException;
import com.example.web.security.MemberDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class MemberApiController {
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final MailService mailService;

    //회원가입 요청
    @PostMapping("/member")
    public ResponseEntity signUp(@Valid @RequestBody MemberSignUpDto memberSignUpDto){
        memberSignUpDto.validPassword();
        String key = memberService.signUp(memberSignUpDto);
        new Thread(() -> mailService.signUp(memberSignUpDto.getEmail(), key)).start();
        return new ResponseEntity(HttpStatus.OK);
    }

    //회원수정 요청
    @PatchMapping("/member")
    public ResponseEntity update(@AuthenticationPrincipal MemberDetails memberDetails,
                                 @RequestBody MemberUpdateDto memberUpdateDto){
        memberService.update(memberUpdateDto, memberDetails.getUsername(), memberDetails.isOAuth2User());
        return new ResponseEntity(HttpStatus.OK);
    }

    //비밀번호 변경 요청
    @PatchMapping("/member/password")
    public ResponseEntity updatePassword(@AuthenticationPrincipal MemberDetails memberDetails,
                                         @Valid @RequestBody MemberUpdatePasswordDto memberUpdatePasswordDto){
        memberUpdatePasswordDto.validPassword();
        memberService.updatePassword(memberUpdatePasswordDto, memberDetails.getUsername());
        return new ResponseEntity(HttpStatus.OK);
    }

    //회원삭제 요청
    @DeleteMapping("/member")
    public ResponseEntity delete(@AuthenticationPrincipal MemberDetails memberDetails){
        memberService.delete(memberDetails.getUsername(), memberDetails.isOAuth2User());
        return new ResponseEntity(HttpStatus.OK);
    }

    //아이디찾기 요청
    @PostMapping("/findLoginId")
    public ResponseEntity findLoginId(@Valid @RequestBody MemberFindUsernameDto memberFindUsernameDto){
        Optional<Member> findMember = memberRepository.findTop1ByNameAndEmail(memberFindUsernameDto.getName(), memberFindUsernameDto.getEmail());
        Member member = findMember.orElseThrow(() -> new RestApiException(ErrorCode.BAD_REQUEST, "해당하는 회원이 없습니다."));
        new Thread(() -> mailService.findId(memberFindUsernameDto.getEmail(), member.getUsername())).start();
        return new ResponseEntity(HttpStatus.OK);
    }

    //비밀번호찾기 요청
    @PostMapping("/findPassword")
    public ResponseEntity findPassword(@Valid @RequestBody MemberFindPasswordDto memberFindPasswordDto){
        String password = memberService.findPassword(memberFindPasswordDto);
        new Thread(() -> mailService.findPassword(memberFindPasswordDto.getEmail(), password)).start();;
        return new ResponseEntity(HttpStatus.OK);
    }
}
