package com.example.web.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, String> {
    boolean existsById(String id);
    boolean existsByEmail(String email);
    boolean existsByNameAndEmail(String name, String email);
    Optional<MemberEntity> findByNameAndEmail(String name, String email);
}
