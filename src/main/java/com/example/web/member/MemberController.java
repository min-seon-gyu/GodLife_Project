package com.example.web.member;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class MemberController {
    @GetMapping("/member/createSignUp")
    public String createSignUp(){ return "signUp"; }

    @GetMapping("/member/createFindLoginId")
    public String createFindLoginId(){
        return "findId";
    }

    @GetMapping("/member/createFindPassword")
    public String createFindPassword(){
        return "findPassword";
    }

    @GetMapping("/member/createSignUpSuccess")
    public String createSignUpSuccess(){
        return "signUpSuccess";
    }

    @GetMapping("/member/createFindLoginIdSuccess")
    public String createFindLoginIdSuccess(){
        return "findIdSuccess";
    }

    @GetMapping("/member/createFindPasswordSuccess")
    public String createFindPasswordSuccess(){
        return "findPasswordSuccess";
    }
}
