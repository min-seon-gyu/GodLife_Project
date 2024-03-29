# 갓생 프로젝트
- #### 프로젝트 기간 : 2023년 11월 - 2024년 3월
- #### 주제 : 일정 관리 사이트
- #### 맡은 포지션 : 백엔드 서버 개발
- #### 사용 기술 : Java, Spring boot, MySQL, Redis, Jenkins, Docker

## 주요 기능
- #### 회원가입, 회원수정, 회원탈퇴
- #### 로그인, 로그아웃, 아이디 찾기, 비밀번호 찾기
- #### 일정 추가, 일정 삭제, 일정 수정

## 프로젝트를 진행하면서 배운 점

- ### OAuth2
기존 로그인 방식에서 OAuth2 로그인 방식을 추가 구현해보았습니다.

유저 정보에는 이메일 정보가 존재합니다. 그리고 이메일 정보를 통해 아이디를 찾을 수 있는데 이 때 때 가져온 유저의 데이터가 회원가입을 통해 만들어진 유저인지 또는 OAuth2 유저인지 체크하는 로직이 추가로 필요합니다. 이를 방지하기 위해 처음부터 회원가입 전용 유저테이블과 OAuth2 전용 유저 테이블을 분리하였습니다.

OAuth2 로그인 처리 로직을 다음과 같이 구현하였습니다. 해당 함수는 DefaultOAuth2UserService을 상속받는 클래스에 위치하고 있습니다.

```java
 @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        OAuth2MemberInfo oAuth2MemberInfo = null;
        Map<String, Object> attributes = null;

        if(registrationId.equals("naver")){
            oAuth2MemberInfo = new OAuth2MemberNaverInfo(oAuth2User.getAttribute("response"));
            attributes = oAuth2User.getAttribute("response");
        }else if(registrationId.equals("google")){
            oAuth2MemberInfo = new OAuth2MemberGoogleInfo(oAuth2User.getAttributes());
            attributes = oAuth2User.getAttributes();
        }

        String provider = oAuth2MemberInfo.getProvider(); // google
        String providerId = oAuth2MemberInfo.getProviderId();
        String email = oAuth2MemberInfo.getEmail();
        String name = oAuth2MemberInfo.getName();
        String username = provider + "_" + providerId;
        String password = passwordService.encode(passwordService.getRandom());

        Optional<MemberOAuth2> findMember = memberOAuth2Repository.findTop1ByUsername(username);
        MemberOAuth2 memberOAuth2 = null;
        if(findMember.isPresent()){
            memberOAuth2 = findMember.get();
        }else{
            memberOAuth2 = MemberOAuth2.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .provider(provider)
                    .providerId(providerId)
                    .name(name)
                    .role(MemberRole.USER)
                    .build();
            memberOAuth2Repository.save(memberOAuth2);
        }
        MemberSecurityDto memberSecurityDto = MemberSecurityDto.builder().username(memberOAuth2.getUsername())
                .email(memberOAuth2.getEmail())
                .password(memberOAuth2.getPassword())
                .name(memberOAuth2.getName())
                .role(memberOAuth2.getRole().name())
                .build();

        return new MemberDetails(memberSecurityDto, attributes);
    }
```

OAuth2에 대한 학습한 내용을 정리한 글은 개발 블로그에 작성하였습니다.

[OAuth2 정리글](https://velog.io/@gcael/%EA%B0%93%EC%83%9D-%EC%82%AC%EC%9D%B4%ED%8A%B8-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-OAuth)

- ### CI/CD

예전 프로젝트를 진행하면서 반복적인 서버 배포 과정을 개선하기 위해 CI/CD를 구현하고 적용해보았습니다. 해당 내용이 매우 길기 때문에 당시 블로그에 정리한 글로 대체하겠습니다.

[CI/CD 정리글](https://velog.io/@gcael/%EA%B0%93%EC%83%9D-%EC%82%AC%EC%9D%B4%ED%8A%B8-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-CICD)

- ### Redisson

자바 스프링 기반의 웹 애플리케이션은 기본적으로 멀티 스레드 환경에서 구동이 됩니다. 따라서, 여러 스레드가 함께 접근할 수 있는 공유 자원에 대해 Race condition이 발생하지 않도록 별도의 처리가 필요한데 synchronized라는 키워드를 사용하여 처리를 하고 있습니다. 하지만 멀티 프로세스 환경에서 동시성 문제가 발생할 수 있습니다. 이때 해결하는 방법이 분산 락입니다. 그 중 Redisson에서 제공하는 분산 락을 적용해 보았습니다.

갓생 사이트에서 일정은 하루에 최대 5개까지 가능합니다. 이 규칙을 보장하기 위해서 분산 락을 적용하였습니다. 코드에서는 분산 락을 적용할 메소드에 AOP를 활용하여 적용되어있습니다.

```
    @Around("@annotation(com.example.web.common.RedisRock)")
    public Object redissonLock(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        RedissonRock redissonRock = method.getAnnotation(RedissonRock.class);
        RLock rLock = redissonClient.getLock(REDISSON_LOCK);

        try {
            boolean available = rLock.tryLock(redissonRock.waitTime(), redissonRock.leaseTime(), redissonRock.timeUnit());
            if (!available) {
                throw new RestApiException(ErrorCode.INTERNAL_SERVER_ERROR, "락을 획득하지 못했습니다.");
            }
            return aopForTransaction.proceed(joinPoint);
        } catch (InterruptedException  e) {
            throw new InterruptedException();
        } finally {
            try{
                rLock.unlock();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
```


