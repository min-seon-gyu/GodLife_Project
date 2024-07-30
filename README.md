# 갓생 프로젝트
- **기간 : 2023년 11월 - 2024년 04월**
- **담당 : 갓생 사이트 화면 구현 및 백엔드 API 개발**
- **참여 인원 : 1명**
- **개발 환경**
    - **언어 : Java 17, JavaScript**
    - **프레임워크 : Spring Boot 3.2.0, Spring Security**
    - **데이터베이스 : MySQL, Redis**
    - **도구 : Docker, Jenkins, Elasticsearch, Logstash**
- **주요 구현**
    - **Java, Spring Boot를 이용한 RESTful API 설계**
    - **Spring Data JPA 적용**
    - **Spring Security, OAuth2를 활용한 세션 방식 인증/인가 적용**
    - **Redisson을 활용하여 분산 락 적용**
    - **Elasticsearch를 활용한 검색 기능 및 페이징 처리 구현**
    - **Pessimistic Lock을 활용하여 동시성 문제 해결**
    - **Jenkins, Docker를 활용하여 자동 배포 파이프라인 구현**

## 프로젝트 설명

#### 아키텍처 설계
![캡처](https://github.com/user-attachments/assets/5caf2002-4e8c-4ba6-9ce3-89ad044f238a)

#### ERD 설계
![](https://velog.velcdn.com/images/gcael/post/79f4dbee-cbec-4a13-92ee-5e6acc617ed1/image.png)
 
#### 서비스 기능
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
 
## 주요 구현 과정 및 실행 화면

#### [갓생 사이트 프로젝트 v1 - 회원](https://velog.io/@gcael/%EA%B0%93%EC%83%9D-%EC%82%AC%EC%9D%B4%ED%8A%B8-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-v1-%ED%9A%8C%EC%9B%90)
#### [갓생 사이트 프로젝트 v1 - 회원 가입, 로그인 개선](https://velog.io/@gcael/%EA%B0%93%EC%83%9D-%EC%82%AC%EC%9D%B4%ED%8A%B8-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-v1-%ED%9A%8C%EC%9B%90-%EA%B0%80%EC%9E%85-%EB%A1%9C%EA%B7%B8%EC%9D%B8-%EA%B0%9C%EC%84%A0)
#### [갓생 사이트 프로젝트 v1 - 일정](https://velog.io/@gcael/%EA%B0%93%EC%83%9D-%EC%82%AC%EC%9D%B4%ED%8A%B8-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-v1-%EC%9D%BC%EC%A0%95)
#### [갓생 사이트 프로젝트 v1 - 분산 락](https://velog.io/@gcael/%EA%B0%93%EC%83%9D-%EC%82%AC%EC%9D%B4%ED%8A%B8-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-v1-%EB%B6%84%EC%82%B0-%EB%9D%BD-3fqv1vay)
#### [갓생 사이트 프로젝트 v1 - ELK 스택 도입](https://velog.io/@gcael/%EA%B0%93%EC%83%9D-%EC%82%AC%EC%9D%B4%ED%8A%B8-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-v1-ELK-%EC%8A%A4%ED%83%9D-%EB%8F%84%EC%9E%85)
#### [갓생 사이트 프로젝트 v1 - 일정 검색](https://velog.io/@gcael/%EA%B0%93%EC%83%9D-%EC%82%AC%EC%9D%B4%ED%8A%B8-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-v1-%EC%9D%BC%EC%A0%95-%EA%B2%80%EC%83%89)
#### [갓생 사이트 프로젝트 v1 - 일정 변경](https://velog.io/@gcael/%EA%B0%93%EC%83%9D-%EC%82%AC%EC%9D%B4%ED%8A%B8-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-v1-%EC%9D%BC%EC%A0%95-%EB%B3%80%EA%B2%BD)
#### [갓생 사이트 프로젝트 v1 - 회원 탈퇴, 일정 삭제 개선](https://velog.io/@gcael/%EA%B0%93%EC%83%9D-%EC%82%AC%EC%9D%B4%ED%8A%B8-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-v1-%ED%9A%8C%EC%9B%90-%ED%83%88%ED%87%B4-%EC%9D%BC%EC%A0%95-%EC%82%AD%EC%A0%9C-%EA%B0%9C%EC%84%A0)
#### [갓생 사이트 프로젝트 v2 - 상점](https://velog.io/@gcael/%EA%B0%93%EC%83%9D-%EC%82%AC%EC%9D%B4%ED%8A%B8-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-v2-%EC%83%81%EC%A0%90)
#### [갓생 사이트 프로젝트 v2 - 구매, 포인트, 쿠폰, 보유쿠폰](https://velog.io/@gcael/%EA%B0%93%EC%83%9D-%EC%82%AC%EC%9D%B4%ED%8A%B8-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-v2-%EA%B5%AC%EB%A7%A4-%ED%8F%AC%EC%9D%B8%ED%8A%B8-%EC%BF%A0%ED%8F%B0)

    
## 기술적 개선 및 고려

### 분산 락

일정은 하루에 작성할 수 있는 개수가 제한되어 있습니다. 멀티 스레드 환경에서는 이러한 제약을 지키기 위해서 자바에서 제공하는 synchronized 키워드를 활용하여 처리하였습니다.

하지만 멀티 프로세스 환경에서는 synchronized만으로는 해당 문제를 해결할 수 없습니다. 이 문제를 해결하기 위해서 분산 락을 활용하였습니다. 분산 락은 여러 프로세스가 공유 데이터를 제어하기 위한 기술로 해당 문제를 해결하기에 적합하다고 생각하였습니다. 구현 방법으로는 MySQL과 Redis중 Redis를 택하였는데 그 이유로는 기존 서비스에서 이미 Redis를 활용하고 있어 별도의 환경 구축이 필요하지 않았고 인메모리 데이터베이스이기 때문에 성능적으로도 이점이 있기 때문입니다.

분산 락을 적용하는 과정에서 많은 에러를 경험했습니다. 그중에서 대표적으로는 락을 해제하는 시점 설정이 있었습니다. 해당 에러는 트랜잭션의 커밋 시점과 락을 해제하는 시점 차이에서 발생하는 에러로 트랙잭션의 커밋이 락 해제보다 늦게 될 경우에 데이터의 무결성이 깨질 수 있다는 점이었습니다. 이러한 점을 놓치지 않고 테스트를 거쳐 프로젝트에 성공적으로 적용하였습니다.

```java
@Bean
public RedissonClient redissonClient() {
    Config config = new Config();
    config.useSingleServer().setAddress(REDISSON_HOST_PREFIX + host + ":" + port);
    return Redisson.create(config);
}
    
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RedissonRock {
    TimeUnit timeUnit() default SECONDS;
    long waitTime() default 5L;
    long leaseTime() default 5L;
}

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

@Component
public class AopForTransaction {
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Object proceed(final ProceedingJoinPoint joinPoint) throws Throwable {
        return joinPoint.proceed();
    }
}
```

### 비관적 락

상점에서는 구매할 수 있는 상품에는 수량이 있습니다. 그렇기 때문에 많은 유저가 동시에 구매를 하는 가정에서 동시성 문제가 발생할 수 있습니다. 

동시성 문제를 해결하는 방법 중에는 격리 수준 설정과 비관적 락이 있습니다. 격리 수준 설정의 경우는 데이터 무결성이 중요할 때 많이 사용되며, 비관적 락의 경우는 성능이 중요할 때 많이 사용됩니다. 만일 금융 관련 서비스에서의 동시성 문제라면 격리 수준 설정을 통해 문제를 해결하겠지만, 상품 구매 서비스는 비관적 락을 사용해도 괜찮다고 판단하였습니다. 이처럼 성능과 무결성 사이에 트레이드 오브를 서비스의 따라 적용하면 될 것 같습니다.

```java
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select p from Product p where p.id = :id")
    Optional<Product> findByIdPessimisticLock(@Param("id") Long id);
}
```
### ElasticSearch

갓생 사이트에서는 일정 검색 기능이 있습니다. 해당 기능은 관계형 데이터베이스 기반으로 구현되어 있으며 ‘더 보기’ 방식 페이징 처리가 되어있습니다. 하지만 JMeter를 사용하여 성능테스트를 해본 결과 성능이 저조하다는 것을 확인하였습니다. 이러한 성능을 개선하기 위하여 다음과 같은 방안을 적용해보았습니다.

먼저 MySQL의 전문검색을 활용해보았습니다. 일반적인 조건절에서는 인덱스를 활용하여 성능을 개선할 수 있지만 해당 기능은 LIKE 연산을 사용하기 때문에 인덱스를 활용한 성능 개선이 어려워 전문검색을 활용하였고 그 결과 기존 성능의 2배 빠른 성능을 가져올 수 있었습니다. 

다음으로는 ElasticSearch를 활용하여 성능을 올려보았습니다. ElasticSearch는 대표적인 검색 엔진으로 역색인 방식과 샤드를 활용하여 성능이 뛰어나다는 장점이 있습니다. 또한 하나의 쿼리로 카운트까지 알 수 있는 장점이 있습니다. ElasticSearch을 적용함으로써 발생하는 MySQL과의 동기화 문제는 Logstash의 파이프라인을 활용하여 처리하였습니다.

앞서 성능 향상을 위한 방안으로 두 가지를 경험해 보았습니다. 두 방안을 비교했을 때 ElasticSearch를 활용한 방안이 성능적으로 더욱 우수한 것을 확인하였습니다. 또한 한글 형태소 분석기를 통해 검색의 품질도 ElasticSearch가 우수하였습니다. 이러한 이유로 검색 기능을 기존 관계형 데이터베이스 기반에서 ElasticSearch 기반으로 변경하였고 기존 문제를 개선할 수 있었습니다.

```java
@Configuration
public class ElasticSearchConfig{
    @Value("${spring.elasticsearch.hostname}")
    private String hostName;
    @Value("${spring.elasticsearch.port}")
    private int port;
    @Value("${spring.elasticsearch.username}")
    private String username;
    @Value("${spring.elasticsearch.password}")
    private String password;

@Bean
public ElasticsearchClient getElasticSearchClient() {

    CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
    credentialsProvider.setCredentials(AuthScope.ANY,
            new UsernamePasswordCredentials(username, password));

    RestClientBuilder builder = RestClient.builder(new HttpHost(hostName, port, "HTTPS"))
            .setHttpClientConfigCallback(
                httpClientBuilder -> httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider));

    RestClient restClient = builder.build();
    ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
    return new ElasticsearchClient(transport);
    }
}

@GetMapping("/search_schedule/{content}/{pageIndex}")
public ScheduleDocumentPaging find(@AuthenticationPrincipal MemberDetails memberDetails,
                                   @PathVariable(value = "content", required = false) String content,
                                   @PathVariable(value = "pageIndex", required = false) int pageIndex) throws IOException {
    Query termQuery = QueryBuilders.term().field("content").value(FieldValue.of(content)).build()._toQuery();
    Query matchQuery = QueryBuilders.match().field("member_id").query(memberDetails.getId()).build()._toQuery();
    SearchResponse<ScheduleDocument> search = elasticsearchClient.search(s -> s
                    .index("schedule")
                    .query(q -> q
                        .bool(t -> t
                            .must(termQuery, matchQuery)))
                    .from(pageIndex).size(pageSize)
                    .sort(so -> so.field(f -> f
                        .field("local_date")
                            .order(SortOrder.Asc))), ScheduleDocument.class);
    List<ScheduleDocumentResponse> result = new ArrayList<>();
    for (Hit<ScheduleDocument> hit: search.hits().hits()) {
        result.add(new ScheduleDocumentResponse(hit.source()));
    }

    return ScheduleDocumentPaging.builder().
            lst(result).
            nextIndex(pageIndex + pageSize).
            hasNext(search.hits().total().value() > pageIndex + pageSize - 1).
            build();
}
```

엘라스틱 서치를 도입하여 일정 검색 기능을 구현, 검색 기능에 대한 부하테스트(100명의 유저가 지속적으로 요청) 결과

**[JMeter]**

MySQL 전문검색
![mysql](https://github.com/user-attachments/assets/71e30405-942f-459a-ab22-f07b6589f446)

엘라스틱 서치
![엘라스틱서치](https://github.com/user-attachments/assets/4fc4bbcf-6f3d-42d0-977d-fbd23ec225d8)

### 조회 기능 개선(인덱스 설계)


조회 요청에서의 조건 컬럼을 인덱스 설정 및 불필요한 조인 제거, 조회 기능에 대한 부하테스트(100명의 유저가 지속적으로 요청) 결과

**[nGrinder]**

인덱스 설계 전
![노인덱스](https://github.com/user-attachments/assets/db17a94e-3c54-4be9-a86d-329abbd60e23)

인덱스 설계 후
![인덱스](https://github.com/user-attachments/assets/ce3d850d-1e52-41a2-aae6-a4391cb60168)





