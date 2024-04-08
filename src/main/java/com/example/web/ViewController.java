package com.example.web;

import com.example.web.exception.ErrorCode;
import com.example.web.exception.RestApiException;
import com.example.web.member.Member;
import com.example.web.member.MemberRepository;
import com.example.web.member.MemberService;
import com.example.web.redis.SignUpService;
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
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ViewController {

    private final static String PREVIOUS = "previous";
    private final static String NEXT = "next";
    private final MemberService memberService;
    private final SignUpService signUpService;
    private final MemberRepository memberRepository;
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
                       @PathVariable(value = "year", required = false) Integer year,
                       @PathVariable(value = "month", required = false) Integer month,
                       @PathVariable(value = "day", required = false) Integer day){
        if(validCheck(year, month, day)){
            if(pastCheck(year, month, day)){
                model.addAttribute("past", "true");
            }else{
                model.addAttribute("past", "false");
            }
            model.addAttribute("schedules", scheduleRepository.findByMemberIdAndLocalDate(memberDetails.getId(), LocalDate.of(year,month,day)));
            model.addAttribute("name", memberDetails.getName());
            model.addAttribute("day", LocalDate.of(year, month, day).toString());
            return "main";
        }else{
            return "redirect:/schedule/" + getToday();
        }
    }

    @GetMapping("/move/{type}/{date}")
    public String move(@PathVariable(value = "type", required = false) String type,
                       @PathVariable(value = "date", required = false) LocalDate date){
        if(type.equals(PREVIOUS)){
            date = date.minusDays(1L);
        }else if(type.equals(NEXT)){
            date = date.plusDays(1L);
        }else{
            throw new RestApiException(ErrorCode.BAD_REQUEST, "타입이 유효하지 않습니다.");
        }
        String str = date.toString();
        return "redirect:/schedule/" + str.substring(0,4) + "/" + str.substring(5,7) + "/" + str.substring(8);
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
        Optional<Member> findMember = memberRepository.findTop1ByUsername(memberDetails.getUsername());
        Member member = findMember.orElseThrow(() -> new RestApiException(ErrorCode.BAD_REQUEST, "해당하는 회원이 없습니다."));
        model.addAttribute("member", member);
        model.addAttribute("isBasic", !memberDetails.isOAuth());
        return "updateMember";
    }

    private boolean validCheck(Integer year, Integer month, Integer day) {
        if(year == null || month == null || day == null){
            return false;
        }

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

    private boolean pastCheck(Integer year, Integer month, Integer day) {
        return LocalDate.of(year, month, day).isBefore(LocalDate.now());
    }

    private String getToday(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        return now.format(formatter);
    }
}
