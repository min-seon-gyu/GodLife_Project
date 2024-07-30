# 갓생 프로젝트
- **기간 : 2023년 11월 - 2024년 04월**
- **담당 : 갓생 사이트 화면 구현 및 백엔드 API 개발**
- **참여 인원 : 1명**
- **개발 환경**
    - **언어 : Java 17, JavaScript**
    - **프레임워크 : Spring Boot 3.2.0, Spring Security, Spring Data JPA**
    - **데이터베이스 : MySQL, Redis**
    - **도구 : Docker, Jenkins, Elasticsearch, Logstash**

## 프로젝트 설명

### 아키텍처 설계
![캡처](https://github.com/user-attachments/assets/5caf2002-4e8c-4ba6-9ce3-89ad044f238a)

### ERD 설계
![](https://velog.velcdn.com/images/gcael/post/79f4dbee-cbec-4a13-92ee-5e6acc617ed1/image.png)
 
### 서비스 기능
- **회원 기능**
    - 회원가입 시 이메일 인증이 필요합니다.
    - 회원수정, 회원탈퇴, 로그인, 로그아웃을 할 수 있습니다.
    - 이메일을 통해 아이디 / 비밀번호 찾기를 할 수 있습니다.
    - 네이버 / 구글 로그인을 할 수 있습니다.
    - 상품 구매 내역을 확인할 수 있습니다.
- **일정 기능**
    - 날짜를 변경하여 일정을 조회할 수 있습니다.
    - 일정 추가, 일정 삭제, 일정 수정, 일정 완료를 할 수 있습니다.
    - 날짜별 최대 5개의 일정 제약이 있습니다.
    - 일정 완료 시 포인트를 얻을 수 있습니다.
- **검색 기능**
    - 일정을 키워드로 검색할 수 있습니다.
    - 검색 결과가 많은 경우 더보기 기능을 사용할 수 있습니다.
- **상점 기능**
    - ADMIN 계정으로 상품 추가, 상품 삭제, 상품 수정을 할 수 있습니다.
    - 보유 중인 포인트를 사용하여 상품을 구매할 수 있습니다.
- **쿠폰 기능**
    - 보유 중인 쿠폰을 사용하여 주문 금액을 할인받을 수 있습니다.
 
## 구현 과정 및 실행 화면

### [갓생 사이트 프로젝트 로그 설정](https://velog.io/@gcael/%EA%B0%93%EC%83%9D-%EC%82%AC%EC%9D%B4%ED%8A%B8-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%EB%A1%9C%EA%B7%B8-%EC%84%A4%EC%A0%95)
### [갓생 사이트 프로젝트 CI/CD](https://velog.io/@gcael/%EA%B0%93%EC%83%9D-%EC%82%AC%EC%9D%B4%ED%8A%B8-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-CICD)
### [갓생 사이트 프로젝트 Spring Security, OAuth2 1편](https://velog.io/@gcael/%EA%B0%93%EC%83%9D-%EC%82%AC%EC%9D%B4%ED%8A%B8-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-v1-Spring-Security)
### [갓생 사이트 프로젝트 Spring Security, OAuth2 2편](https://velog.io/@gcael/%EA%B0%93%EC%83%9D-%EC%82%AC%EC%9D%B4%ED%8A%B8-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-v1-OAuth)
### [갓생 사이트 프로젝트 v1 - 회원](https://velog.io/@gcael/%EA%B0%93%EC%83%9D-%EC%82%AC%EC%9D%B4%ED%8A%B8-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-v1-%ED%9A%8C%EC%9B%90)
### [갓생 사이트 프로젝트 v1 - 회원 가입, 로그인 개선](https://velog.io/@gcael/%EA%B0%93%EC%83%9D-%EC%82%AC%EC%9D%B4%ED%8A%B8-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-v1-%ED%9A%8C%EC%9B%90-%EA%B0%80%EC%9E%85-%EB%A1%9C%EA%B7%B8%EC%9D%B8-%EA%B0%9C%EC%84%A0)
### [갓생 사이트 프로젝트 v1 - 일정](https://velog.io/@gcael/%EA%B0%93%EC%83%9D-%EC%82%AC%EC%9D%B4%ED%8A%B8-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-v1-%EC%9D%BC%EC%A0%95)
### [갓생 사이트 프로젝트 v1 - 분산 락](https://velog.io/@gcael/%EA%B0%93%EC%83%9D-%EC%82%AC%EC%9D%B4%ED%8A%B8-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-v1-%EB%B6%84%EC%82%B0-%EB%9D%BD-3fqv1vay)
### [갓생 사이트 프로젝트 v1 - ELK 스택 도입](https://velog.io/@gcael/%EA%B0%93%EC%83%9D-%EC%82%AC%EC%9D%B4%ED%8A%B8-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-v1-ELK-%EC%8A%A4%ED%83%9D-%EB%8F%84%EC%9E%85)
### [갓생 사이트 프로젝트 v1 - 일정 검색](https://velog.io/@gcael/%EA%B0%93%EC%83%9D-%EC%82%AC%EC%9D%B4%ED%8A%B8-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-v1-%EC%9D%BC%EC%A0%95-%EA%B2%80%EC%83%89)
### [갓생 사이트 프로젝트 v1 - 일정 변경](https://velog.io/@gcael/%EA%B0%93%EC%83%9D-%EC%82%AC%EC%9D%B4%ED%8A%B8-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-v1-%EC%9D%BC%EC%A0%95-%EB%B3%80%EA%B2%BD)
### [갓생 사이트 프로젝트 v1 - 회원 탈퇴, 일정 삭제 개선](https://velog.io/@gcael/%EA%B0%93%EC%83%9D-%EC%82%AC%EC%9D%B4%ED%8A%B8-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-v1-%ED%9A%8C%EC%9B%90-%ED%83%88%ED%87%B4-%EC%9D%BC%EC%A0%95-%EC%82%AD%EC%A0%9C-%EA%B0%9C%EC%84%A0)
### [갓생 사이트 프로젝트 v2 - 상점](https://velog.io/@gcael/%EA%B0%93%EC%83%9D-%EC%82%AC%EC%9D%B4%ED%8A%B8-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-v2-%EC%83%81%EC%A0%90)
### [갓생 사이트 프로젝트 v2 - 구매, 포인트, 쿠폰, 보유쿠폰](https://velog.io/@gcael/%EA%B0%93%EC%83%9D-%EC%82%AC%EC%9D%B4%ED%8A%B8-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-v2-%EA%B5%AC%EB%A7%A4-%ED%8F%AC%EC%9D%B8%ED%8A%B8-%EC%BF%A0%ED%8F%B0)

## 기술적 개선 및 고려

### Redisson을 활용하여 분산 락 적용

- 멀티 프로세스 환경에서 프로세스 간 데이터 공유 문제를 Redis Lock을 이용해 동시성 제어 구현
    - pub/sub을 이용해 Lock 획득을 시도하는 RedissonClient를 통해 CPU 낭비 방지

### 조회 기능 개선(인덱스 설계)

- 조회 요청에서 사용되는 조건 컬럼에 복합 인덱스를 설정하여 조회 기능 개선
**[nGrinder]**

인덱스 설계 전
![노인덱스](https://github.com/user-attachments/assets/db17a94e-3c54-4be9-a86d-329abbd60e23)

인덱스 설계 후
![인덱스](https://github.com/user-attachments/assets/ce3d850d-1e52-41a2-aae6-a4391cb60168)

### Elasticsearch를 활용한 검색 기능 및 페이징 처리 구현

- Full Text Index(ngram parser)에서 Elasticsearch로 변경하여 검색 기능 개선
    - 데이터베이스와 동기화를 위하여 Logstash를 활용한 배치 처리 적용

**[JMeter]**

MySQL 전문검색
![mysql](https://github.com/user-attachments/assets/71e30405-942f-459a-ab22-f07b6589f446)

엘라스틱 서치
![엘라스틱서치](https://github.com/user-attachments/assets/4fc4bbcf-6f3d-42d0-977d-fbd23ec225d8)

### Pessimistic Lock을 활용하여 동시성 문제 해결

- 

### 인증/인가 적용
- Spring Security, OAuth2 인증/인가 적용
    - OAuth2 클라이언트를 사용하여 Naver, Google 로그인 구현

### 비동기 처리 개선
- Third-party API를 호출할 때 사용자 경험 저하 발생
    - 스레드 풀을 사용하여 비동기적으로 실행하여 개선

### CI/CD 환경 구축

- Jenkins, Docker, Docker Hub를 활용하여 테스트-빌드-배포 자동화

### 로그 설정

- 실행 환경별 로그 출력 설정
    - 편의성을 높이기 위해 로그 레벨별 파일 저장 기능 추가
