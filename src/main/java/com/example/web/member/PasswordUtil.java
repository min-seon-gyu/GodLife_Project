package com.example.web.member;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PasswordUtil {
    private final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private final int size = 12;
    private final SecureRandom sr = new SecureRandom();
    public String create(){
        StringBuffer sb = new StringBuffer();
        for(int i = 0 ; i < size ; i++){
            sb.append(chars.charAt(sr.nextInt(chars.length())));
        }
        return sb.toString();
    }

    public String encryption(String password){
        return "";
    }
}
