package com.example.web.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    final private MemberService memberService;

    @GetMapping("/member/signUp")
    public String signUp(Model model){
        model.addAttribute("memberSignUpDto", new MemberSignUpDto());
        return "signUp";
    }

    @GetMapping("/member/findId")
    public String findId(Model model){
        model.addAttribute("memberFindIdDto", new MemberFindIdDto());
        return "findId";
    }

    @GetMapping("/member/findPassword")
    public String findPassword(Model model){
        model.addAttribute("memberFindPasswordDto", new MemberFindPasswordDto());
        return "findPassword";
    }

    @PostMapping("/member/")
    public String signUp(MemberSignUpDto memberSignUpDto){
        log.info("회원가입 요청 - id : {}, name : {}, email : {}", memberSignUpDto.getId(), memberSignUpDto.getName(), memberSignUpDto.getEmail());
        return "signUpSuccess";
    }

    @GetMapping("/member/id")
    public String findId(MemberFindIdDto memberFindIdDto){
        log.info("아이디 찾기 요청 - name : {}, email : {}", memberFindIdDto.getName(), memberFindIdDto.getEmail());
        return "findIdSuccess";
    }

    @GetMapping("/member/password")
    public String findPassword(MemberFindPasswordDto memberFindPasswordDto){
        log.info("아이디 찾기 요청 - id : {}, name : {}, email : {}", memberFindPasswordDto.getId(), memberFindPasswordDto.getName(), memberFindPasswordDto.getEmail());
        return "findPasswordSuccess";
    }

    @GetMapping("/member/checkId")
    @ResponseBody
    public boolean isDuplicationId(@RequestParam("id") String id){
        boolean result = memberService.checkId(id);
        log.info("아이디 중복 확인 요청 - id : {}, result : {}", id, result);
        return result;
    }

    @GetMapping("/member/checkEmail")
    @ResponseBody
    public boolean isDuplicationEmail(@RequestParam("email") String email){
        boolean result = memberService.checkEmail(email);
        log.info("이메일 중복 확인 요청 - email : {}, result : {}", email, result);
        return result;
    }
}
