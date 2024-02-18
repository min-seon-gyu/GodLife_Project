package com.example.web;

import com.example.web.member.Member;
import com.example.web.member.MemberRepository;
import com.example.web.security.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ViewController {

    private final MemberRepository memberRepository;

    @GetMapping("/")
    public String Test(@RequestParam(value = "error", required = false)String error,
                       @AuthenticationPrincipal MemberDetails memberDetails,
                       Model model){
        if(memberDetails == null){
            model.addAttribute("error", error);
            return "index";
        }else{
            return "main";
        }
    }

    @GetMapping("/createSignUpView")
    public String createSignUpView(){ return "createSignUp"; }

    @GetMapping("/createSignUpSuccessView")
    public String createSignUpSuccessView(){
        return "createSignUpSuccess";
    }

    @GetMapping("/createFindLoginIdView")
    public String createFindLoginIdView(){
        return "createFindLoginId";
    }

    @GetMapping("/createFindLoginIdSuccessView")
    public String createFindLoginIdSuccessView(){
        return "createFindLoginIdSuccess";
    }

    @GetMapping("/createFindPasswordView")
    public String createFindPasswordFormView(){
        return "createFindPassword";
    }

    @GetMapping("/createFindPasswordSuccessView")
    public String createFindPasswordSuccessView(){
        return "createFindPasswordSuccess";
    }

    @GetMapping("/createUpdateView")
    public String createUpdateView(@AuthenticationPrincipal MemberDetails memberDetails, Model model){
        Optional<Member> findMember = memberRepository.findTop1ByLoginId(memberDetails.getUsername());
        if(findMember.isPresent()){
            Member member = findMember.get();
            model.addAttribute("member", member);
        }
        return "createUpdate";
    }

    @GetMapping("/createUpdateSuccessView")
    public String createUpdateSuccessView(){
        return "createUpdateSuccess";
    }
}
