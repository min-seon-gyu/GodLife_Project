package com.example.web.member;

import com.example.web.exception.ErrorCode;
import com.example.web.exception.RestApiException;
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
        if(!memberSignUpDto.validPassword()){
            throw new RestApiException(ErrorCode.BAD_REQUEST, "비밀번호 체크가 맞지 않습니다.");
        }
        memberService.signUp(memberSignUpDto);
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
    public ResponseEntity findLoginId(@Valid @RequestBody MemberFindLoginIdDto memberFindLoginIdDto){
        memberService.findLoginId(memberFindLoginIdDto);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/findPassword")
    public ResponseEntity findPassword(@Valid @RequestBody MemberFindPasswordDto memberFindPasswordDto){
        memberService.findPassword(memberFindPasswordDto);
        return new ResponseEntity(HttpStatus.OK);
    }
}
