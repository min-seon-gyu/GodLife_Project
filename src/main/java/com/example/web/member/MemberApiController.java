package com.example.web.member;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class MemberApiController {
    private final MemberService memberService;
    @PostMapping("/member")
    public ResponseEntity signUp(@Valid @RequestBody MemberSignUpDto memberSignUpDto){
        memberService.signUp(memberSignUpDto);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/member/logout")
    public ResponseEntity logout(){
        memberService.logout();
        return new ResponseEntity(HttpStatus.OK);
    }

    @PatchMapping("/member")
    public ResponseEntity update(@RequestBody MemberUpdateDto memberUpdateDto){
        memberService.update(memberUpdateDto);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/member")
    public ResponseEntity delete(){
        memberService.delete();
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/findLoginId")
    public ResponseEntity findLoginId(@RequestBody MemberFindLoginIdDto memberFindLoginIdDto){
        memberService.findLoginId(memberFindLoginIdDto);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/findPassword")
    public ResponseEntity findPassword(@RequestBody MemberFindPasswordDto memberFindPasswordDto){
        memberService.findPassword(memberFindPasswordDto);
        return new ResponseEntity(HttpStatus.OK);
    }
}
