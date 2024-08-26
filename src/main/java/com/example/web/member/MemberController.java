package com.example.web.member;

import com.example.web.common.MailService;
import com.example.web.exception.ErrorCode;
import com.example.web.exception.RestApiException;
import com.example.web.item.ItemService;
import com.example.web.memberCoupon.MemberCouponService;
import com.example.web.order.OrderService;
import com.example.web.redis.SignUpService;
import com.example.web.schedule.ScheduleService;
import com.example.web.security.MemberDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class MemberController {
    private final MemberService memberService;
    private final SignUpService signUpService;
    private final MailService mailService;
    private final ScheduleService scheduleService;
    private final OrderService orderService;
    private final ItemService itemService;
    private final MemberCouponService memberCouponService;
    private final MemberRepository memberRepository;

    //회원가입 요청
    @PostMapping("/member")
    public ResponseEntity signUp(@Valid @RequestBody MemberSignUpDto memberSignUpDto){
        memberSignUpDto.validPassword();
        String key = memberService.signUp(memberSignUpDto);
        signUpService.setData(key, memberSignUpDto);
        mailService.signUp(memberSignUpDto.getEmail(), key);
        return new ResponseEntity(HttpStatus.OK);
    }

    //회원수정 요청
    @PatchMapping("/member")
    public ResponseEntity update(@AuthenticationPrincipal MemberDetails memberDetails,
                                 @RequestBody MemberUpdateDto memberUpdateDto){
        Long id = memberService.update(memberUpdateDto, memberDetails.getId());
        return ResponseEntity.ok(id);
    }

    //비밀번호 변경 요청
    @PatchMapping("/member/password")
    public ResponseEntity updatePassword(@AuthenticationPrincipal MemberDetails memberDetails,
                                         @Valid @RequestBody MemberUpdatePasswordDto memberUpdatePasswordDto){
        memberUpdatePasswordDto.validPassword();
        Long id = memberService.updatePassword(memberUpdatePasswordDto, memberDetails.getId());
        return ResponseEntity.ok(id);
    }

    //회원삭제 요청
    @DeleteMapping("/member")
    public ResponseEntity delete(@AuthenticationPrincipal MemberDetails memberDetails) throws IOException {
        scheduleService.deleteByMemberId(memberDetails.getId());
        orderService.deleteByMemberId(memberDetails.getId());
        itemService.deleteByMemberId(memberDetails.getId());
        memberCouponService.deleteByMemberId(memberDetails.getId());
        memberService.delete(memberDetails.getId());
        return new ResponseEntity(HttpStatus.OK);
    }

    //아이디찾기 요청
    @PostMapping("/findLoginId")
    public ResponseEntity findLoginId(@Valid @RequestBody MemberFindUsernameDto memberFindUsernameDto){
        Optional<Member> findMember = memberRepository.findTop1ByNameAndEmailAndType(memberFindUsernameDto.getName(), memberFindUsernameDto.getEmail(), MemberType.BASIC);
        Member member = findMember.orElseThrow(() -> new RestApiException(ErrorCode.BAD_REQUEST, "해당하는 회원이 존재하지 않습니다."));
        mailService.findId(memberFindUsernameDto.getEmail(), member.getUsername());
        return new ResponseEntity(HttpStatus.OK);
    }

    //비밀번호찾기 요청
    @PostMapping("/findPassword")
    public ResponseEntity findPassword(@Valid @RequestBody MemberFindPasswordDto memberFindPasswordDto){
        String password = memberService.findPassword(memberFindPasswordDto);
        mailService.findPassword(memberFindPasswordDto.getEmail(), password);
        return new ResponseEntity(HttpStatus.OK);
    }
}
