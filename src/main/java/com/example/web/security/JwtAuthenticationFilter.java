package com.example.web.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Collections;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter  {

    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String header = request.getHeader(AuthConstants.AUTH_HEADER);
            log.debug("header Check: {}", header);
            if (header != null) {
                String token = JwtProvider.getTokenFromHeader(header);
                log.debug("token Check: {}", token);
                if (token != null && JwtProvider.isValidToken(token)) {
                    String userId = JwtProvider.getUserIdFromToken(token);
                    JwtAuthenticationToken authenticationToken = new JwtAuthenticationToken(userId, token, Collections.emptyList());
                    Authentication authentication = authenticationManager.authenticate(authenticationToken);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (AuthenticationException e) {
            log.debug("error");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
        filterChain.doFilter(request, response);
    }
}
