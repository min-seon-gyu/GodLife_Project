package com.example.web.member;

import com.example.web.common.ExeTimer;
import com.example.web.common.MailService;
import com.example.web.exception.ErrorCode;
import com.example.web.exception.RestApiException;
import com.example.web.security.MemberDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class MemberApiController {
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final MemberOAuth2Repository memberOAuth2Repository;
    private final MailService mailService;

    //회원가입 요청
    @PostMapping("/member")
    @ExeTimer
    @ResponseBody
    public ResponseEntity signUp(@Valid @RequestBody MemberSignUpDto memberSignUpDto){
        if(!memberSignUpDto.validPassword()){
            throw new RestApiException(ErrorCode.BAD_REQUEST, "비밀번호 체크가 맞지 않습니다.");
        }
        String key = memberService.signUp(memberSignUpDto);
        new Thread(() -> mailService.signUp(memberSignUpDto.getEmail(), key)).start();
        return new ResponseEntity(HttpStatus.OK);
    }

    //회원가입 인증 요청
    @GetMapping("/member/{key}")
    public String signUpConfirm(@PathVariable("key") String key){
        memberService.signUpConfirm(key);
        return "createSignUpConfirm";
    }

    //회원수정 기본 데이터
    @GetMapping("/createUpdateView")
    public String createUpdateView(@AuthenticationPrincipal MemberDetails memberDetails, Model model){
        Optional<?> findMember = memberDetails.isOAuth2User() ?
                memberOAuth2Repository.findTop1ByUsername(memberDetails.getUsername()) : memberRepository.findTop1ByUsername(memberDetails.getUsername());
        Object object = findMember.orElseThrow(() -> new RestApiException(ErrorCode.BAD_REQUEST, "해당하는 회원이 없습니다."));
        model.addAttribute("member", object);
        return "createUpdate";
    }

    //회원수정 요청
    @PatchMapping("/member")
    @ResponseBody
    public ResponseEntity update(@AuthenticationPrincipal MemberDetails memberDetails,
                                 @RequestBody MemberUpdateDto memberUpdateDto){
        memberService.update(memberUpdateDto, memberDetails.getUsername(), memberDetails.isOAuth2User());
        return new ResponseEntity(HttpStatus.OK);
    }

    //회원삭제 요청
    @DeleteMapping("/member")
    @ResponseBody
    public ResponseEntity delete(@AuthenticationPrincipal MemberDetails memberDetails){
        memberService.delete(memberDetails.getUsername(), memberDetails.isOAuth2User());
        return new ResponseEntity(HttpStatus.OK);
    }

    //아이디찾기 요청
    @PostMapping("/findLoginId")
    @ResponseBody
    public ResponseEntity findLoginId(@Valid @RequestBody MemberFindUsernameDto memberFindUsernameDto){
        Optional<Member> findMember = memberRepository.findTop1ByNameAndEmail(memberFindUsernameDto.getName(), memberFindUsernameDto.getEmail());
        Member member = findMember.orElseThrow(() -> new RestApiException(ErrorCode.BAD_REQUEST, "해당하는 회원이 없습니다."));
        new Thread(() -> mailService.findId(memberFindUsernameDto.getEmail(), member.getUsername())).start();
        return new ResponseEntity(HttpStatus.OK);
    }

    //비밀번호찾기 요청
    @PostMapping("/findPassword")
    @ResponseBody
    public ResponseEntity findPassword(@Valid @RequestBody MemberFindPasswordDto memberFindPasswordDto){
        String password = memberService.findPassword(memberFindPasswordDto);
        new Thread(() -> mailService.findPassword(memberFindPasswordDto.getEmail(), password)).start();;
        return new ResponseEntity(HttpStatus.OK);
    }
}
