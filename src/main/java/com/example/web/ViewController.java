package com.example.web;

import com.example.web.security.MemberDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ViewController {
    @GetMapping("/")
    public String mainView(@RequestParam(value = "error", required = false)String error,
                       @AuthenticationPrincipal MemberDetails memberDetails,
                       Model model){
        if(memberDetails == null){
            model.addAttribute("error", error);
            return "index";
        }else{
            model.addAttribute("name", memberDetails.getName());
            return "main";
        }
    }

    @GetMapping("/createSignUpView")
    public String createSignUpView(){ return "createSignUp"; }

    @GetMapping("/createSignUpSuccessView")
    public String createSignUpSuccessView(){
        return "createSignUpSuccess";
    }

    @GetMapping("/createFindUsernameView")
    public String createFindLoginIdView(){
        return "createFindUsername";
    }

    @GetMapping("/createFindUsernameSuccessView")
    public String createFindLoginIdSuccessView(){
        return "createFindUsernameSuccess";
    }

    @GetMapping("/createFindPasswordView")
    public String createFindPasswordFormView(){
        return "createFindPassword";
    }

    @GetMapping("/createFindPasswordSuccessView")
    public String createFindPasswordSuccessView(){
        return "createFindPasswordSuccess";
    }

    @GetMapping("/createUpdateSuccessView")
    public String createUpdateSuccessView(){
        return "createUpdateSuccess";
    }
    @GetMapping("/createDeleteSuccessView")
    public String createDeleteSuccessView(){
        return "createDeleteSuccess";
    }
}
