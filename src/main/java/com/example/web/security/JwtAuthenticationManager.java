package com.example.web.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationManager implements AuthenticationManager {

    private final JwtMemberDetailsService memberDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = (String) authentication.getCredentials();
        String username = (String) authentication.getPrincipal();
        JwtMemberDetails jwtMemberDetails = memberDetailsService.loadUserByUsername(username);
        if (username.equals(JwtProvider.getUserIdFromToken(token))) {
            return new JwtAuthenticationToken(jwtMemberDetails.getUsername(), token, jwtMemberDetails.getAuthorities());
        }
        throw new BadCredentialsException("Authentication failed");
    }
}
