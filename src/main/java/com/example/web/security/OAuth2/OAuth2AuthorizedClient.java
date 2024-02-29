package com.example.web.security.OAuth2;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "oauth2_authorized_client")
public class OAuth2AuthorizedClient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OAUTH2TOKEN_ID")
    private Long id;
    @NotNull
    @Column(length = 100)
    private String clientRegistrationId;
    @NotNull
    @Column(length = 200)
    private String principalName;
    @NotNull
    @Column(length = 100)
    private String accessTokenType;
    @NotNull
    @Lob
    private String accessTokenValue;
    @NotNull
    private LocalDateTime accessTokenIssuedAt;
    @NotNull
    private LocalDateTime accessTokenExpiresAt;
    @Column(length = 1000)
    private String accessTokenScopes;
    @Lob
    private String refreshTokenValue;
    private LocalDateTime refreshTokenIssuedAt;
    @NotNull
    private LocalDateTime createdAt;
}
