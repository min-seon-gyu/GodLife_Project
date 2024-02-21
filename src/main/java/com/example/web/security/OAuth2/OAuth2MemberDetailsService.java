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

import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

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

        if(registrationId.equals("naver")){
            oAuth2MemberInfo = new MemberNaverInfo(oAuth2User.getAttribute("response"));
        }else if(registrationId.equals("google")){
            Map<String, Object> attributes = oAuth2User.getAttributes();
            Set<String> strings = attributes.keySet();
            Iterator<String> iterator = strings.iterator();
            while(iterator.hasNext()){
                String key =iterator.next();
                System.out.println("key = " + key);
                Object value = attributes.get(key).toString();
                System.out.println("value = " + value);
            }
            oAuth2MemberInfo = new MemberGoogleInfo(oAuth2User.getAttributes());
        }

        String provider = oAuth2MemberInfo.getProvider(); // google
        String providerId = oAuth2MemberInfo.getProviderId();
        String email = oAuth2MemberInfo.getEmail();
        String name = oAuth2MemberInfo.getName();
        String username = provider + "_" + providerId;
        String password = passwordService.encode("임시비밀번호");

        Optional<Member> findMember = memberRepository.findTop1ByUsername(username);
        Member member = null;
        if(findMember.isPresent()){
            member = findMember.get();
        }else{
            member = Member.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .provider(provider)
                    .providerId(providerId)
                    .name(name)
                    .role(MemberRole.USER)
                    .build();
            memberRepository.save(member);
        }
        MemberSecurityDto memberSecurityDto = MemberSecurityDto.builder().username(member.getUsername())
                .email(member.getEmail())
                .password(member.getPassword())
                .name(member.getName())
                .role(member.getRole().name())
                .build();

        return new MemberDetails(memberSecurityDto, oAuth2User.getAttributes());
    }
}
