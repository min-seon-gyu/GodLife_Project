package com.example.web.security.OAuth2;

import com.example.web.member.*;
import com.example.web.security.MemberDetails;
import com.example.web.security.MemberSecurityDto;
import com.example.web.security.PasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OAuth2MemberDetailsService extends DefaultOAuth2UserService {

    private final PasswordService passwordService;
    private final MemberOAuth2Repository memberOAuth2Repository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        OAuth2MemberInfo oAuth2MemberInfo = null;
        Map<String, Object> attributes = null;

        if(registrationId.equals("naver")){
            oAuth2MemberInfo = new OAuth2MemberNaverInfo(oAuth2User.getAttribute("response"));
            attributes = oAuth2User.getAttribute("response");
        }else if(registrationId.equals("google")){
            oAuth2MemberInfo = new OAuth2MemberGoogleInfo(oAuth2User.getAttributes());
            attributes = oAuth2User.getAttributes();
        }

        String provider = oAuth2MemberInfo.getProvider(); // google
        String providerId = oAuth2MemberInfo.getProviderId();
        String email = oAuth2MemberInfo.getEmail();
        String name = oAuth2MemberInfo.getName();
        String username = provider + "_" + providerId;
        String password = passwordService.encode("임시비밀번호");

        Optional<MemberOAuth2> findMember = memberOAuth2Repository.findTop1ByUsername(username);
        MemberOAuth2 memberOAuth2 = null;
        if(findMember.isPresent()){
            memberOAuth2 = findMember.get();
        }else{
            memberOAuth2 = MemberOAuth2.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .provider(provider)
                    .providerId(providerId)
                    .name(name)
                    .role(MemberRole.USER)
                    .build();
            memberOAuth2Repository.save(memberOAuth2);
        }
        MemberSecurityDto memberSecurityDto = MemberSecurityDto.builder().username(memberOAuth2.getUsername())
                .email(memberOAuth2.getEmail())
                .password(memberOAuth2.getPassword())
                .name(memberOAuth2.getName())
                .role(memberOAuth2.getRole().name())
                .build();

        return new MemberDetails(memberSecurityDto, attributes);
    }
}
