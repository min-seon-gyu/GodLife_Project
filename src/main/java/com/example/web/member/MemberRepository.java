package com.example.web.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByUsername(String username);
    boolean existsByEmailAndIsOAuth(String email, boolean isOAuth);
    @Query("select m from Member m left join fetch m.items i where m.id = :id")
    Optional<Member> findMemberById(@Param("id") Long id);
    Optional<Member> findTop1ByNameAndEmailAndIsOAuth(String name, String email, boolean isOAuth);
    Optional<Member> findTop1ByUsernameAndNameAndEmailAndIsOAuth(String username, String name, String email, boolean isOAuth);
    Optional<Member> findTop1ByUsername(String username);
}
