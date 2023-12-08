package com.example.web.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {


    private final MemberRepository memberRepository;


    public boolean checkId(String id){
        return memberRepository.existsById(id);
    }

    public boolean checkEmail(String email){
        return memberRepository.existsByEmail(email);
    }

    public boolean checkNameAndEmail(MemberFindIdDto memberFindIdDto){
        return memberRepository.existsByNameAndEmail(memberFindIdDto.getName(), memberFindIdDto.getEmail());
    }

    public boolean signUp(MemberSignUpDto memberSignUpDto){
        return false;
    }

    public Optional<MemberEntity> findId(MemberFindIdDto memberFindIdDto){
        return memberRepository.findByNameAndEmail(memberFindIdDto.getName(), memberFindIdDto.getEmail());
    }

    public boolean findPassword(MemberFindPasswordDto memberFindPasswordDto){
        return false;
    }
}
