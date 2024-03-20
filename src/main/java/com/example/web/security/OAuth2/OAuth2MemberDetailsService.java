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
    private final MemberRepository memberRepository;

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
        String password = passwordService.encode(passwordService.getRandom());

        Optional<Member> findMember = memberRepository.findTop1ByUsername(username);
        Member member = findMember.orElseGet(() -> buildMember(username, password, email, provider, providerId, name));

        MemberSecurityDto memberSecurityDto = MemberSecurityDto.builder()
                .id(member.getId())
                .username(member.getUsername())
                .email(member.getEmail())
                .password(member.getPassword())
                .name(member.getName())
                .role(member.getRole().name())
                .build();

        return new MemberDetails(memberSecurityDto, attributes);
    }

    private Member buildMember(String username, String password, String email, String provider, String providerId, String name){
        Member member = Member.builder()
                .username(username)
                .password(password)
                .email(email)
                .provider(provider)
                .providerId(providerId)
                .name(name)
                .role(MemberRole.USER)
                .isOAuth(true)
                .build();
        memberRepository.save(member);
        return member;
    }
}
