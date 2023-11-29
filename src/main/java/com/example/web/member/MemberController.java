package com.example.web.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DirectFieldBindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberValidator memberValidator;

    @GetMapping("/signUp")
    public String signUp(Model model){
        model.addAttribute("memberSignUpDto", new MemberSignUpDto());
        //model.addAttribute("member", new Member());
        return "signUp";
    }

    @GetMapping("/findId")
    public String findId(Model model){
        model.addAttribute("memberFindIdDto", new MemberFindIdDto());
        return "findId";
    }

    @GetMapping("/findPassword")
    public String findPassword(Model model){
        model.addAttribute("memberFindPasswordDto", new MemberFindPasswordDto());
        return "findPassword";
    }

    @PostMapping("/signUp")
    public String signUp(MemberSignUpDto memberSignUpDto, BindingResult bindingResult, Model model){
        if(memberValidator.supports(MemberSignUpDto.class)){
            Errors errors = new DirectFieldBindingResult(memberSignUpDto, "memberSignUpDto");
            memberValidator.validate(memberSignUpDto, errors);
            bindingResult.addAllErrors(errors);
        }

        if(bindingResult.hasErrors()){
            return "signUp";
        }
        return "index";
    }

    @PostMapping("/findId")
    public String findId(MemberFindIdDto memberFindIdDto, BindingResult bindingResult, Model model){
        if(memberValidator.supports(MemberFindIdDto.class)){
            Errors errors = new DirectFieldBindingResult(memberFindIdDto, "memberFindIdDto");
            memberValidator.validate(memberFindIdDto, errors);
            bindingResult.addAllErrors(errors);
        }

        if(bindingResult.hasErrors()){
            return "findId";
        }
        return "index";
    }

    @PostMapping("/findPassword")
    public String findPassword(MemberFindPasswordDto memberFindPasswordDto, BindingResult bindingResult, Model model){
        if(memberValidator.supports(MemberFindPasswordDto.class)){
            Errors errors = new DirectFieldBindingResult(memberFindPasswordDto, "memberFindPasswordDto");
            memberValidator.validate(memberFindPasswordDto, errors);
            bindingResult.addAllErrors(errors);
        }

        if(bindingResult.hasErrors()){
            return "findPassword";
        }
        return "index";
    }
}
