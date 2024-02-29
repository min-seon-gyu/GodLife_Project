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
    public String createSignUpView(){ return "signUp"; }

    @GetMapping("/createSignUpSuccessView")
    public String createSignUpSuccessView(){
        return "signUpSuccess";
    }

    @GetMapping("/createFindUsernameView")
    public String createFindLoginIdView(){
        return "findUsername";
    }

    @GetMapping("/createFindUsernameSuccessView")
    public String createFindLoginIdSuccessView(){
        return "findUsernameSuccess";
    }

    @GetMapping("/createFindPasswordView")
    public String createFindPasswordFormView(){
        return "findPassword";
    }

    @GetMapping("/createFindPasswordSuccessView")
    public String createFindPasswordSuccessView(){
        return "findPasswordSuccess";
    }

    @GetMapping("/createUpdateMemberSuccessView")
    public String createUpdateMemberSuccessView(){
        return "updateMemberSuccess";
    }

    @GetMapping("/createDeleteMemberSuccessView")
    public String createDeleteSuccessView(){
        return "deleteMemberSuccess";
    }

    @GetMapping("/createUpdatePasswordView")
    public String createUpdatePasswordView(){
        return "updatePassword";
    }
    @GetMapping("/createUpdatePasswordSuccessView")
    public String createUpdatePasswordSuccessView(){
        return "updatePasswordSuccess";
    }
}
