package com.example.web.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
public class MemberTest {

    @Autowired
    private MemberController memberController;

    @BeforeEach
    void before(){

    }

    @AfterEach
    void after(){

    }

    @Test
    void 아이디중복체크(){
        //아이디가 있는 경우
        String id = "123456";
        boolean resultA = memberController.isDuplicationId(id);
        assertThat(resultA).isEqualTo(true);

        //아이디가 없는 경우
        boolean resultB = memberController.isDuplicationId("");
        assertThat(resultB).isEqualTo(false);
    }

    @Test
    void 이메일중복체크(){
        //이메일이 있는 경우
        String email = "gcael@naver.com";
        boolean resultA = memberController.isDuplicationEmail(email);
        assertThat(resultA).isEqualTo(true);

        //이메일이 없는 경우
        boolean resultB = memberController.isDuplicationEmail("");
        assertThat(resultB).isEqualTo(false);
    }

    @Test
    void 회원가입(){
        MemberEntity memberEntity = MemberEntity.CreateMemberEntity("123123", "123", "123", "gcael@naver.com");
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
