package com.example.web;

import com.example.web.security.MemberDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/")
    public String Test(@AuthenticationPrincipal MemberDetails memberDetails){
        if(memberDetails == null){
            return "index";
        }
        return "main";
    }

    @GetMapping("/createSignUpView")
    public String createSignUpView(){ return "createSignUp"; }

    @GetMapping("/createFindLoginIdView")
    public String createFindLoginIdView(){
        return "createFindLoginId";
    }

    @GetMapping("/createFindPasswordView")
    public String createFindPasswordFormView(){
        return "createFindPassword";
    }

    @GetMapping("/createSignUpSuccessView")
    public String createSignUpSuccessView(){
        return "createSignUpSuccess";
    }

    @GetMapping("/createFindLoginIdSuccessView")
    public String createFindLoginIdSuccessView(){
        return "createFindLoginIdSuccess";
    }

    @GetMapping("/createFindPasswordSuccessView")
    public String createFindPasswordSuccessView(){
        return "createFindPasswordSuccess";
    }

    @GetMapping("/createUpdateView")
    public String createUpdateView(){
        return "createFindPasswordSuccess";
    }
}
