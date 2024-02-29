package com.example.web.member;

import com.example.web.common.ClassToMapConvert;
import com.example.web.exception.ErrorCode;
import com.example.web.exception.RestApiException;
import com.example.web.redis.RedisUtil;
import com.example.web.security.PasswordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final MemberOAuth2Repository memberOAuth2Repository;
    private final PasswordService passwordService;
    private final RedisUtil redisUtil;

    public String signUp(MemberSignUpDto memberSignUpDto){
        log.info("회원가입 요청 - id : {}, name : {}, email : {}", memberSignUpDto.getUsername(), memberSignUpDto.getName(), memberSignUpDto.getEmail());
        if(validationCheck(memberSignUpDto)){
            log.info("회원가입 요청 실패- id : {}, name : {}, email : {}, {}", memberSignUpDto.getUsername(), memberSignUpDto.getName(), memberSignUpDto.getEmail(), "이미 회원가입 했습니다.");
            throw new RestApiException(ErrorCode.BAD_REQUEST, "이미 가입된 회원정보입니다.");
        }
        try {
            String key = memberSignUpDto.getUsername() + "_" + passwordService.getRandom();
            Map<Object, Object> map = ClassToMapConvert.convert(memberSignUpDto);
            redisUtil.setData(key, map, 5l);
            return key;
        }catch (IllegalAccessException e){
            e.printStackTrace();
            throw new RestApiException(ErrorCode.BAD_REQUEST, "다시 한번 시도해주세요");
        }
    }

    @Transactional
    public void signUpConfirm(String key){
        Map<Object, Object> map = redisUtil.getData(key);
        Member member = Member.builder()
                .username((String) map.get("username"))
                .email((String) map.get("email"))
                .name((String) map.get("name"))
                .password(passwordService.encode((String) map.get("password")))
                .role(MemberRole.USER)
                .build();
        memberRepository.save(member);
    }

    @Transactional
    public void update(MemberUpdateDto memberUpdateDto, String username, boolean isOAuth2User) {
        if(isOAuth2User){
            Optional<MemberOAuth2> findMember = memberOAuth2Repository.findTop1ByUsername(username);
            MemberOAuth2 member = findMember.orElseThrow(() -> new RestApiException(ErrorCode.BAD_REQUEST, "해당하는 회원이 없습니다."));
            member.update(memberUpdateDto);
        }else{
            Optional<Member> findMember = memberRepository.findTop1ByUsername(username);
            Member member = findMember.orElseThrow(() -> new RestApiException(ErrorCode.BAD_REQUEST, "해당하는 회원이 없습니다."));
            member.update(memberUpdateDto);
        }
    }

    @Transactional
    public void delete(String username, boolean isOAuth2User) {
        if(isOAuth2User){
            Optional<MemberOAuth2> findMember = memberOAuth2Repository.findTop1ByUsername(username);
            memberOAuth2Repository.delete(findMember.orElseThrow(() -> new RestApiException(ErrorCode.BAD_REQUEST, "해당하는 회원이 없습니다.")));
        }else{
            Optional<Member> findMember = memberRepository.findTop1ByUsername(username);
            memberRepository.delete(findMember.orElseThrow(() -> new RestApiException(ErrorCode.BAD_REQUEST, "해당하는 회원이 없습니다.")));
        }
    }

    @Transactional
    public String findPassword(MemberFindPasswordDto memberFindPasswordDto) {
        log.info("비밀번호 찾기 요청 - id : {}, name : {}, email : {}", memberFindPasswordDto.getUsername(), memberFindPasswordDto.getName(), memberFindPasswordDto.getEmail());
        Optional<Member> findMember = memberRepository.findTop1ByUsernameAndNameAndEmail(memberFindPasswordDto.getUsername(), memberFindPasswordDto.getName(), memberFindPasswordDto.getEmail());
        Member member = findMember.orElseThrow(() -> new RestApiException(ErrorCode.BAD_REQUEST, "해당하는 회원이 없습니다."));
        String password = passwordService.getRandom();
        String encodePassword = passwordService.encode(password);
        member.changePassword(encodePassword);

        return password;
    }


    private boolean validationCheck(MemberSignUpDto memberSignUpDto){
        return memberRepository.existsByUsername(memberSignUpDto.getUsername()) || memberRepository.existsByEmail(memberSignUpDto.getEmail());
    }
}
