package com.example.web.member;

import com.example.web.common.MailService;
import com.example.web.exception.ErrorCode;
import com.example.web.exception.RestApiException;
import com.example.web.redis.SignUpService;
import com.example.web.schedule.ScheduleService;
import com.example.web.security.MemberDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RequiredArgsConstructor
@RestController
public class MemberApiController {
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);
    private final MemberService memberService;
    private final ScheduleService scheduleService;
    private final SignUpService signUpService;
    private final MemberRepository memberRepository;
    private final MailService mailService;

    //회원가입 요청
    @PostMapping("/member")
    public ResponseEntity signUp(@Valid @RequestBody MemberSignUpDto memberSignUpDto){
        memberSignUpDto.validPassword();
        String key = memberService.signUp(memberSignUpDto);
        signUpService.setData(key, memberSignUpDto);
        executorService.submit(() -> mailService.signUp(memberSignUpDto.getEmail(), key));
        return new ResponseEntity(HttpStatus.OK);
    }

    //회원수정 요청
    @PatchMapping("/member")
    public ResponseEntity update(@AuthenticationPrincipal MemberDetails memberDetails,
                                 @RequestBody MemberUpdateDto memberUpdateDto){
        memberService.update(memberUpdateDto, memberDetails.getId());
        return new ResponseEntity(HttpStatus.OK);
    }

    //비밀번호 변경 요청
    @PatchMapping("/member/password")
    public ResponseEntity updatePassword(@AuthenticationPrincipal MemberDetails memberDetails,
                                         @Valid @RequestBody MemberUpdatePasswordDto memberUpdatePasswordDto){
        memberUpdatePasswordDto.validPassword();
        memberService.updatePassword(memberUpdatePasswordDto, memberDetails.getId());
        return new ResponseEntity(HttpStatus.OK);
    }

    //회원삭제 요청
    @DeleteMapping("/member")
    public ResponseEntity delete(@AuthenticationPrincipal MemberDetails memberDetails){
        scheduleService.deleteAll(memberDetails.getId());
        memberService.delete(memberDetails.getId());
        return new ResponseEntity(HttpStatus.OK);
    }

    //아이디찾기 요청
    @PostMapping("/findLoginId")
    public ResponseEntity findLoginId(@Valid @RequestBody MemberFindUsernameDto memberFindUsernameDto){
        Optional<Member> findMember = memberRepository.findTop1ByNameAndEmailAndIsOAuth(memberFindUsernameDto.getName(), memberFindUsernameDto.getEmail(), false);
        Member member = findMember.orElseThrow(() -> new RestApiException(ErrorCode.BAD_REQUEST, "해당하는 회원이 없습니다."));
        executorService.submit(() -> mailService.findId(memberFindUsernameDto.getEmail(), member.getUsername()));
        return new ResponseEntity(HttpStatus.OK);
    }

    //비밀번호찾기 요청
    @PostMapping("/findPassword")
    public ResponseEntity findPassword(@Valid @RequestBody MemberFindPasswordDto memberFindPasswordDto){
        String password = memberService.findPassword(memberFindPasswordDto);
        executorService.submit(() -> mailService.findPassword(memberFindPasswordDto.getEmail(), password));
        return new ResponseEntity(HttpStatus.OK);
    }
}
