package com.example.web.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Slf4j
@RestController
public class MemberApiController {
    private final MemberService memberService;

    @PostMapping("/member/loginId")
    public ResponseEntity findLoginId(@RequestBody MemberFindLoginIdDto memberFindLoginIdDto){
        try {
            memberService.findLoginId(memberFindLoginIdDto);
        }
        catch (Exception e){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/member/password")
    public ResponseEntity findPassword(@RequestBody MemberFindPasswordDto memberFindPasswordDto){
        try {
            memberService.findPassword(memberFindPasswordDto);
        }
        catch (Exception e){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/member")
    public ResponseEntity signUp(@RequestBody MemberSignUpDto memberSignUpDto){
        try {
            memberService.signUp(memberSignUpDto);
        }
        catch (Exception e){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);
    }
}
