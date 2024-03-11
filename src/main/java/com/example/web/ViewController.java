package com.example.web;

import com.example.web.exception.ErrorCode;
import com.example.web.exception.RestApiException;
import com.example.web.member.MemberOAuth2Repository;
import com.example.web.member.MemberRepository;
import com.example.web.member.MemberService;
import com.example.web.redis.SignUpService;
import com.example.web.schedule.Schedule;
import com.example.web.schedule.ScheduleRepository;
import com.example.web.security.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ViewController {

    private final MemberService memberService;
    private final SignUpService signUpService;
    private final MemberRepository memberRepository;
    private final MemberOAuth2Repository memberOAuth2Repository;
    private final ScheduleRepository scheduleRepository;

    @GetMapping("/")
    public String init(@AuthenticationPrincipal MemberDetails memberDetails,
                       @RequestParam(value = "error", required = false)String error, Model model){
        if(memberDetails == null) model.addAttribute("login", "false");
        else model.addAttribute("login", "true");
        model.addAttribute("error", error);
        return "index";
    }


    @GetMapping(value = {"/schedule/**", "/schedule/{year}/{month}/{day}"})
    public String schedule(@AuthenticationPrincipal MemberDetails memberDetails, Model model,
                       @PathVariable(value = "year", required = false) String year,
                       @PathVariable(value = "month", required = false) String month,
                       @PathVariable(value = "day", required = false) String day){
        if(validCheck(year, month, day)){
            if(pastCheck(year, month, day)){
                model.addAttribute("past", "true");
            }else{
                model.addAttribute("past", "false");
            }
            List<Schedule> schedules = scheduleRepository.findByUsernameAndLocalDate(memberDetails.getUsername(), LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day)));

            StringBuilder sb = new StringBuilder();
            sb.append(year).append("-").append(month).append("-").append(day);

            model.addAttribute("schedules", schedules);
            model.addAttribute("name", memberDetails.getName());
            model.addAttribute("day", sb.toString());
            return "main";
        }else{
            return "redirect:/schedule/" + getToday();
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

    //회원가입 인증 요청
    @GetMapping("/member/{key}")
    public String signUpConfirm(@PathVariable("key") String key){
        Map<Object, Object> data = signUpService.getData(key);
        memberService.signUpConfirm(data);
        return "signUpConfirm";
    }

    //회원수정 기본 데이터
    @GetMapping("/createUpdateMemberView")
    public String createUpdateView(@AuthenticationPrincipal MemberDetails memberDetails, Model model){
        Optional<?> findMember = memberDetails.isOAuth2User() ?
                memberOAuth2Repository.findTop1ByUsername(memberDetails.getUsername()) : memberRepository.findTop1ByUsername(memberDetails.getUsername());
        Object object = findMember.orElseThrow(() -> new RestApiException(ErrorCode.BAD_REQUEST, "해당하는 회원이 없습니다."));
        model.addAttribute("member", object);
        model.addAttribute("isBasic", !memberDetails.isOAuth2User());
        return "updateMember";
    }

    private boolean validCheck(String y, String m, String d) {
        if(y == null || m == null || d == null){
            return false;
        }

        int year = Integer.parseInt(y);
        int month = Integer.parseInt(m);
        int day = Integer.parseInt(d);

        if(year < 2000 || year > 2099) return false;
        switch (month){
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                if(day < 1 || day > 31) return false;
                break;
            case 2:
                if(year % 4 == 0){
                    if(day < 1 || day > 29) return false;
                }else{
                    if(day < 1 || day > 28) return false;
                }
                break;
            case 4:
            case 9:
            case 6:
            case 11:
                if(day < 1 || day > 30) return false;
                break;
            default :
                return false;
        }
        return true;
    }

    private boolean pastCheck(String y, String m, String d) {
        return LocalDate.of(Integer.parseInt(y), Integer.parseInt(m), Integer.parseInt(d)).isBefore(LocalDate.now());
    }

    private String getToday(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        return now.format(formatter);
    }
}
