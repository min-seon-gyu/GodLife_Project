package com.example.web.security.OAuth2;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;
import org.springframework.stereotype.Component;

//application.yml -> class 에서 Oauth2 설정
//이점 - 좀 더 다양하고 세밀하게 설정이 가능하다.
//주의할 점 - application.yml 에서는 기본적으로 세팅되는 값을 class 에서는 명시해서 작성해야한다.
@Component
public class SocialClientRegistration {

    public ClientRegistration naverClientRegistration() {

        return ClientRegistration.withRegistrationId("naver")
                .clientId("tfKeIufKatlvVK4ulTHG")
                .clientSecret("z2T8S7s977")
                .redirectUri("http://localhost:8080/login/oauth2/code/naver")
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .scope("name", "email")
                .authorizationUri("https://nid.naver.com/oauth2.0/authorize")
                .tokenUri("https://nid.naver.com/oauth2.0/token")
                .userInfoUri("https://openapi.naver.com/v1/nid/me")
                .userNameAttributeName("response")
                .build();
    }

    public ClientRegistration googleClientRegistration() {

        return ClientRegistration.withRegistrationId("google")
                .clientId("968328616945-s8lq0b9nnu4b58uk89ui4qfc76bqai5s.apps.googleusercontent.com")
                .clientSecret("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")
                .redirectUri("http://localhost:8080/login/oauth2/code/google")
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .scope("profile", "email")
                .authorizationUri("https://accounts.google.com/o/oauth2/v2/auth")
                .tokenUri("https://www.googleapis.com/oauth2/v4/token")
                .jwkSetUri("https://www.googleapis.com/oauth2/v3/certs")
                .issuerUri("https://accounts.google.com")
                .userInfoUri("https://www.googleapis.com/oauth2/v3/userinfo")
                .userNameAttributeName(IdTokenClaimNames.SUB)
                .build();
    }
}
