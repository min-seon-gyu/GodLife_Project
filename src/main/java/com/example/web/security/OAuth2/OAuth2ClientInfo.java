package com.example.web.security.OAuth2;


import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class OAuth2ClientInfo {

    @Value("${spring.security.naver.clientId}")
    private String naverClientId;
    @Value("${spring.security.naver.clientSecret}")
    private String naverClientSecret;
    @Value("${spring.security.google.clientId}")
    private String googleClientId;
    @Value("${spring.security.google.clientSecret}")
    private String googleClientSecret;
}
