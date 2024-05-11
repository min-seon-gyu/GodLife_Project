package com.example.web.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByUsername(String username);
    boolean existsByEmailAndType(String email, MemberType type);
    @Query("select m from Member m left join fetch m.items i where m.id = :id")
    Optional<Member> findMemberById(@Param("id") Long id);
    Optional<Member> findTop1ByNameAndEmailAndType(String name, String email, MemberType isOAuth);
    Optional<Member> findTop1ByUsernameAndNameAndEmailAndType(String username, String name, String email, MemberType isOAuth);
    Optional<Member> findTop1ByUsername(String username);
}
