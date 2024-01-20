package com.example.web.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {
    boolean existsByLoginId(String loginId);
    boolean existsByEmail(String email);
    Optional<Member> findTop1ByNameAndEmail(String name, String email);
    Optional<Member> findTop1ByLoginIdAndNameAndEmail(String loginId, String name, String email);
    Optional<Member> findTop1ByLoginId(String loginId);
}
