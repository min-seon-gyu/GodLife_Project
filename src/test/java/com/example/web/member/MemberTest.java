package com.example.web.member;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@ExtendWith(SpringExtension.class)
public class MemberTest {

    @Autowired
    private MemberApiController memberApiController;
    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void before(){
        Member member = Member.builder()
                .loginId("123456")
                .email("gcael@naver.com")
                .name("name")
                .build();
        memberRepository.save(member);
    }

    @Test
    @Transactional
    void 회원가입(){
        MemberSignUpDto memberSignUpDto = new MemberSignUpDto();
        memberSignUpDto.setLoginId("test");
        memberSignUpDto.setName("test");
        memberSignUpDto.setPassword("test");
        memberSignUpDto.setEmail("test");
        ResponseEntity responseEntity = memberApiController.signUp(memberSignUpDto);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @Transactional
    void 아이디찾기(){
        MemberFindLoginIdDto memberFindPasswordDto = new MemberFindLoginIdDto();

        //존재하지 않는 아이디
        memberFindPasswordDto.setName("");
        memberFindPasswordDto.setEmail("");
        ResponseEntity responseEntity1 = memberApiController.findLoginId(memberFindPasswordDto);
        assertThat(responseEntity1.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        //존재하는 아이디
        memberFindPasswordDto.setName("name");
        memberFindPasswordDto.setEmail("gcael@naver.com");
        ResponseEntity responseEntity2 = memberApiController.findLoginId(memberFindPasswordDto);
        assertThat(responseEntity2.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @Transactional
    void 비밀번호찾기(){
        MemberFindPasswordDto memberFindPasswordDto = new MemberFindPasswordDto();

        //존재하지 않는 아이디
        memberFindPasswordDto.setLoginId("");
        memberFindPasswordDto.setName("");
        memberFindPasswordDto.setEmail("");
        ResponseEntity responseEntity1 = memberApiController.findPassword(memberFindPasswordDto);
        assertThat(responseEntity1.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        //존재하는 아이디
        memberFindPasswordDto.setLoginId("123456");
        memberFindPasswordDto.setName("name");
        memberFindPasswordDto.setEmail("gcael@naver.com");
        ResponseEntity responseEntity2 = memberApiController.findPassword(memberFindPasswordDto);
        assertThat(responseEntity2.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @Transactional
    void 로그인(){

    }

    @Test
    @Transactional
    void 로그아웃(){

    }

    @Test
    @Transactional
    void 회원수정(){

    }

    @Test
    @Transactional
    void 회원삭제(){

    }
}
