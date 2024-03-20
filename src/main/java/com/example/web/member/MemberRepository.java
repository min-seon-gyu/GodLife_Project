package com.example.web.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByUsername(String username);
    boolean existsByEmailAndIsOAuth(String email, boolean isOAuth);
    Optional<Member> findTop1ByNameAndEmailAndIsOAuth(String name, String email, boolean isOAuth);
    Optional<Member> findTop1ByUsernameAndNameAndEmailAndIsOAuth(String username, String name, String email, boolean isOAuth);
    Optional<Member> findTop1ByUsername(String username);
}
