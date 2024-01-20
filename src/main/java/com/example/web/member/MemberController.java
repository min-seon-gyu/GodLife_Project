package com.example.web.member;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class MemberController {
    @GetMapping("/member/createSignUp")
    public String createSignUp(){ return "createSignUp"; }

    @GetMapping("/member/createFindLoginId")
    public String createFindLoginId(){
        return "createFindLoginId";
    }

    @GetMapping("/member/createFindPassword")
    public String createFindPassword(){
        return "createFindPassword";
    }

    @GetMapping("/member/createSignUpSuccess")
    public String createSignUpSuccess(){
        return "createSignUpSuccess";
    }

    @GetMapping("/member/createFindLoginIdSuccess")
    public String createFindLoginIdSuccess(){
        return "createFindLoginIdSuccess";
    }

    @GetMapping("/member/createFindPasswordSuccess")
    public String createFindPasswordSuccess(){
        return "createFindPasswordSuccess";
    }
}
