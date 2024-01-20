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
        try {
            memberService.signUp(memberSignUpDto);
            return new ResponseEntity(HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/member/login")
    public ResponseEntity login(@RequestBody MemberLoginDto memberLoginDto){
        try {
            String jwt = memberService.login(memberLoginDto);
            return new ResponseEntity(jwt, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/member/logout")
    public ResponseEntity logout(){
        try {
            memberService.logout();
            return new ResponseEntity(HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/member")
    public ResponseEntity update(@RequestBody MemberUpdateDto memberUpdateDto){
        try {
            memberService.update(memberUpdateDto);
            return new ResponseEntity(HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/member")
    public ResponseEntity delete(){
        try {
            memberService.delete();
            return new ResponseEntity(HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/findLoginId")
    public ResponseEntity findLoginId(@RequestBody MemberFindLoginIdDto memberFindLoginIdDto){
        try {
            memberService.findLoginId(memberFindLoginIdDto);
            return new ResponseEntity(HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/findPassword")
    public ResponseEntity findPassword(@RequestBody MemberFindPasswordDto memberFindPasswordDto){
        try {
            memberService.findPassword(memberFindPasswordDto);
            return new ResponseEntity(HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
}
