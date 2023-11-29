package com.example.web.member;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class MemberTest {



    @BeforeEach
    void before(){

    }

    @AfterEach
    void after(){

    }

    @Test
    void 회원가입(){
        MemberEntity memberEntity = MemberEntity.CreateMemberEntity("123123", "123", "123", new Date());

    }

    @Test
    void 로그인(){

    }

    @Test
    void 로그아웃(){

    }

    @Test
    void 회원수정(){

    }

    @Test
    void 회원삭제(){

    }

    @Test
    void 아이디찾기(){

    }

    @Test
    void 비밀번호찾기(){

    }
}
