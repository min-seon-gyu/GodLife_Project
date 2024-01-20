package com.example.web.member;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.security.SecureRandom;

@Component
public class PasswordService {
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private int size = 12;
    private SecureRandom sr = new SecureRandom();

    public String create(){
        StringBuffer sb = new StringBuffer();
        for(int i = 0 ; i < size ; i++){
            sb.append(chars.charAt(sr.nextInt(chars.length())));
        }
        return sb.toString();
    }

    public String encryption(String password){
        return passwordEncoder.encode(password);
    }

    public boolean match(CharSequence rawPassword, String encodedPassword){
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
