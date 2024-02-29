package com.example.web.member;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberOAuth2Repository extends JpaRepository<MemberOAuth2, Long> {
    Optional<MemberOAuth2> findTop1ByUsername(String username);
}
