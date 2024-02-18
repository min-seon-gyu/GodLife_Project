package com.example.web.security;

import com.example.web.security.OAuth2.OAuth2MemberDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Arrays;

@Configuration
@EnableWebSecurity // 필터 체인에 등록
@RequiredArgsConstructor
public class SecurityConfig{

    private OAuth2MemberDetailsService oAuth2MemberDetailsService;
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    //Oauth2 적용
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(c -> c.configurationSource(corsConfigurationSource()))
                .csrf((csrf) -> csrf.disable())
                .httpBasic((basic) -> basic.disable())
                .authorizeHttpRequests(request -> {
                    //인증이 필요한 요청
                    request.requestMatchers(HttpMethod.GET,"/createMyPageView").authenticated();
                    //ADMIN 권한이 필요한 요청
                    //request.requestMatchers("/test").hasAnyAuthority("ADMIN");
                    request.anyRequest().permitAll();
                })
                .formLogin((login) -> login
                        .loginPage("/")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/")
                        .failureHandler((request, response, exception) -> {
                            response.sendRedirect("/?error=true");
                        }))
                .logout((logout) -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessHandler((request, response, authentication) -> {
                            request.getSession().invalidate();
                            response.sendRedirect("/");
                        }))
                .oauth2Login((oauth2) -> oauth2
                        .loginPage("/")
                        .userInfoEndpoint((userInfoEndpointConfig -> userInfoEndpointConfig
                                .userService(oAuth2MemberDetailsService))));
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("DELETE", "GET", "POST", "PUT", "PATCH"));
        configuration.setAllowedHeaders(Arrays.asList("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
