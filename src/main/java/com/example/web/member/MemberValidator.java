package com.example.web.member;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class MemberValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return MemberSignUpDto.class.equals(clazz) | MemberFindIdDto.class.equals(clazz) | MemberFindPasswordDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if(target instanceof MemberSignUpDto member){
            if(isIdNotValid(member.getId())){
                errors.rejectValue("id", "NotValid", "아이디를 확인해주세요.");
            }
            if(isPasswordNotValid(member.getPassword()) || isPasswordCheckNotValid(member.getPassword()) || isPasswordNotEquals(member.getPassword(), member.getPasswordCheck())){
                errors.rejectValue("passwordCheck", "NotValid", "비밀번호를 확인해주세요.");
            }
            if(isNameNotValid(member.getName())){
                errors.rejectValue("name", "NotValid", "이름을 확인해주세요.");
            }
            if(isEmailNotValid(member.getEmail())){
                errors.rejectValue("email", "NotValid", "이메일을 확인해주세요.");
            }
            if(isDateNotValid(member.getDate())){
                errors.rejectValue("date", "NotValid", "생년월일을 확인해주세요.");
            }
        }

        if(target instanceof MemberFindIdDto member){
            if(isNameNotValid(member.getName())){
                errors.rejectValue("name", "NotValid", "이름을 확인해주세요.");
            }
            if(isNameNotValid(member.getEmail())){
                errors.rejectValue("email", "NotValid", "이메일을 확인해주세요.");
            }
        }

        if(target instanceof MemberFindPasswordDto member){
            if(isIdNotValid(member.getId())){
                errors.rejectValue("id", "NotValid", "아이디를 확인해주세요.");
            }
            if(isNameNotValid(member.getName())){
                errors.rejectValue("name", "NotValid", "이름을 확인해주세요.");
            }
            if(isNameNotValid(member.getEmail())){
                errors.rejectValue("email", "NotValid", "이메일을 확인해주세요.");
            }
        }
    }

    private boolean isIdNotValid(String id){
        return id.isEmpty() ? true : false;
    }

    private boolean isPasswordNotValid(String password){
        return password.isEmpty() ? true : false;
    }

    private boolean isPasswordCheckNotValid(String passwordCheck){
        return passwordCheck.isEmpty() ? true : false;
    }

    private boolean isPasswordNotEquals(String password, String passwordCheck){
        return password.equals(passwordCheck) ? false : true;
    }

    private boolean isNameNotValid(String name){
        return name.isEmpty() ? true : false;
    }

    private boolean isEmailNotValid(String email){
        return email.isEmpty() ? true : false;
    }

    private boolean isDateNotValid(String date){
        return date.isEmpty() ? true : false;
    }
}
