package com.example.web.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
@RequiredArgsConstructor
public class PasswordService {
    private final PasswordEncoder passwordEncoder;
    private final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private final int size = 12;
    private final SecureRandom sr = new SecureRandom();

    public String getRandomPassword(){
        StringBuffer sb = new StringBuffer();
        for(int i = 0 ; i < size ; i++){
            sb.append(chars.charAt(sr.nextInt(chars.length())));
        }
        return sb.toString();
    }

    public String encode(String password){
        return passwordEncoder.encode(password);
    }

    public boolean matches(CharSequence rawPassword, String encodedPassword){
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
