package com.example.web.security.jwt;

import com.example.web.security.MemberDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    @Data
    public static class MemberLoginDto{
        private String loginId;
        private String password;
    }
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        ObjectMapper om = new ObjectMapper();
        MemberLoginDto memberLoginDto = null;

        try {
            memberLoginDto = om.readValue(request.getInputStream(), MemberLoginDto.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        memberLoginDto.getLoginId(),
                        memberLoginDto.getPassword());

        // authenticate() 함수가 호출 되면 인증 프로바이더가 유저 디테일 서비스의 loadUserByUsername(토큰의 첫번째 파라메터) 를 호출하고
        // UserDetails를 리턴받아서 토큰의 두번째 파라메터(credential)과 UserDetails(DB값)의 getPassword()함수로 비교해서 동일하면
        // Authentication 객체를 만들어서 필터체인으로 리턴해준다.

        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult){
        System.out.println("Success");
        MemberDetails memberDetails = (MemberDetails) authResult.getPrincipal();
        String username = memberDetails.getUsername();
        String role = memberDetails.getAuthorities().iterator().next().getAuthority();
        response.addHeader("Authorization", "Bearer " + jwtUtil.createJwt(username, role));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        System.out.println("Fail");
        response.setStatus(401);
    }
}
