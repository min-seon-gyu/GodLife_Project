package com.example.web.security;

import com.example.web.security.OAuth2.CustomOAuth2AuthorizedClientService;
import com.example.web.security.OAuth2.OAuth2MemberDetailsService;
import com.example.web.security.OAuth2.SocialClientRegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Arrays;

@Configuration
@EnableWebSecurity // 필터 체인에 등록
@RequiredArgsConstructor
public class SecurityConfig{

    private final OAuth2MemberDetailsService oAuth2MemberDetailsService;
    private final SocialClientRegistrationRepository socialClientRegistrationRepository;
    private final CustomOAuth2AuthorizedClientService CustomOAuth2AuthorizedClientService;
    private final JdbcTemplate jdbcTemplate;
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
                    request.requestMatchers("/createUpdateMemberView.").authenticated();
                    request.requestMatchers("/createUpdateMemberSuccessView").authenticated();
                    request.requestMatchers("/createUpdatePasswordView").authenticated();
                    request.requestMatchers("/createUpdatePasswordSuccessView").authenticated();
                    request.requestMatchers("/createStoreView").authenticated();
                    request.requestMatchers("/schedule/**").authenticated();
                    request.requestMatchers("/item/**").authenticated();
                    request.requestMatchers("/order/**").authenticated();
                    request.requestMatchers("/coupon/**").authenticated();
                    //ADMIN 권한이 필요한 요청
                    request.requestMatchers("/product/**").hasAnyAuthority("ADMIN");
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
                            response.sendRedirect("/");
                        }))
                .oauth2Login((oauth2) -> oauth2
                        .loginPage("/")
                        .defaultSuccessUrl("/")
                        .clientRegistrationRepository(socialClientRegistrationRepository.clientRegistrationRepository())
                        .authorizedClientService(CustomOAuth2AuthorizedClientService.oAuth2AuthorizedClientService(jdbcTemplate, socialClientRegistrationRepository.clientRegistrationRepository()))
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
