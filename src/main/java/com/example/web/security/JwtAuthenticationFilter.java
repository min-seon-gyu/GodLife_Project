package com.example.web.security;

import com.example.web.member.MemberLoginDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        System.out.println("로그인 시도");

        ObjectMapper om = new ObjectMapper();
        MemberLoginDto memberLoginDto = null;

        try {
            memberLoginDto = om.readValue(request.getInputStream(), MemberLoginDto.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("memberLoginDto = " + memberLoginDto);

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        memberLoginDto.getLoginId(),
                        memberLoginDto.getPassword());

        // authenticate() 함수가 호출 되면 인증 프로바이더가 유저 디테일 서비스의 loadUserByUsername(토큰의 첫번째 파라메터) 를 호출하고
        // UserDetails를 리턴받아서 토큰의 두번째 파라메터(credential)과 UserDetails(DB값)의 getPassword()함수로 비교해서 동일하면
        // Authentication 객체를 만들어서 필터체인으로 리턴해준다.


        Authentication authentication =
                authenticationManager.authenticate(authenticationToken);

        System.out.println("authentication = " + authentication);

        MemberDetails principalDetails = (MemberDetails) authentication.getPrincipal();
        System.out.println("Authentication : "+principalDetails.getMember().getLoginId());
        System.out.println("Authentication : "+principalDetails.getMember().getPassword());

        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult){
        System.out.println("Success");
        String token = jwtUtil.createJwt((MemberDetails) authResult.getPrincipal());
        response.addHeader("Authorization", "Bearer " + token);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        System.out.println("Fail");
        super.unsuccessfulAuthentication(request, response, failed);
    }
}
