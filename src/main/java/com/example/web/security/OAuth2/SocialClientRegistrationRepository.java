package com.example.web.security.OAuth2;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;

@Configuration
@RequiredArgsConstructor
public class SocialClientRegistrationRepository {
    private final SocialClientRegistration socialClientRegistration;

    public ClientRegistrationRepository clientRegistrationRepository() {

        return new InMemoryClientRegistrationRepository(socialClientRegistration.naverClientRegistration(), socialClientRegistration.googleClientRegistration());
    }

}
