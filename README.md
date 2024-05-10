# 갓생 프로젝트

- **프로젝트 기간 : 2023년 11월 ~ ing**  
- **주제 : 일정 관리 사이트**  
- **맡은 포지션 : 백엔드 서버 개발**  

## 프로젝트 설명

#### 적용 기술
- Java, Spring Boot를 이용한 API 개발
- Jenkins, Docker를 활용하여 CI/CD 파이프라인 구현
- Spring Security, OAuth2를 활용하여 인증/인가 적용
- Redisson을 활용하여 분산 락 적용
- Pessimistic Lock을 활용하여 동시성 문제 해결
- 엘라스틱 서치를 활용한 검색 기능 및 페이징 처리 구현
- 로그 설정 적용
 
#### v2 Service(2024.04 ~ )
- **일정 기능**
    - 일정 완료 시 포인트를 얻을 수 있습니다.
- **상점 기능**
    - ADMIN 계정으로 상품 추가, 상품 삭제, 상품 수정을 할 수 있습니다.
    - 보유 중인 포인트를 사용하여 상품을 구매할 수 있습니다.
- **쿠폰 기능**
    - 보유 중인 쿠폰을 사용하여 주문 금액을 할인받을 수 있습니다.
- **아이템 기능**
    - 보유 중인 아이템을 사용할 수 있습니다.(아이템 효과 미구현)
- **회원 기능**
    - 상품 구매 내역을 확인할 수 있습니다.

#### v1 Service(2023.12 ~ 2024.04)
- **회원 기능**
    - 회원가입 시 이메일 인증이 필요합니다.
    - 회원수정, 회원탈퇴, 로그인, 로그아웃을 할 수 있습니다.
    - 이메일을 통해 아이디 / 비밀번호 찾기를 할 수 있습니다.
    - 네이버 / 구글 로그인을 할 수 있습니다.
- **일정 기능**
    - 날짜를 변경하여 일정을 조회할 수 있습니다.
    - 일정 추가, 일정 삭제, 일정 수정, 일정 완료를 할 수 있습니다.
    - 날짜별 최대 5개의 일정 제약이 있습니다.
- **검색 기능**
    - 일정을 키워드로 검색할 수 있습니다.
    - 검색 결과가 많은 경우 더보기 기능을 사용할 수 있습니다.
 
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

    
## 새로 배운 내용

### 분산 락

일정은 하루에 작성할 수 있는 개수가 제한되어 있습니다. 멀티 스레드 환경에서는 이러한 제약을 지키기 위해서 자바에서 제공하는 synchronized 키워드를 활용하여 처리하였습니다.

하지만 멀티 프로세스 환경에서는 synchronized만으로는 해당 문제를 해결할 수 없습니다. 이 문제를 해결하기 위해서 분산 락을 활용하였습니다. 분산 락은 여러 프로세스가 공유 데이터를 제어하기 위한 기술로 해당 문제를 해결하기에 적합하다고 생각하였습니다. 구현 방법으로는 MySQL과 Redis중 Redis를 택하였는데 그 이유로는 기존 서비스에서 이미 Redis를 활용하고 있어 별도의 환경 구축이 필요하지 않았고 인메모리 데이터베이스이기 때문에 성능적으로도 이점이 있기 때문입니다.

분산 락을 적용하는 과정에서 많은 에러를 경험했습니다. 그중에서 대표적으로는 락을 해제하는 시점 설정이 있었습니다. 해당 에러는 트랜잭션의 커밋 시점과 락을 해제하는 시점 차이에서 발생하는 에러로 트랙잭션의 커밋이 락 해제보다 늦게 될 경우에 데이터의 무결성이 깨질 수 있다는 점이었습니다. 이러한 점을 놓치지 않고 테스트를 거쳐 프로젝트에 성공적으로 적용하였습니다.

결과적으로 Redis방식의 분산 락을 적용하여 공유 데이터를 제어할 수 있었고 멀티 프로세스 환경에서도 데이터의 무결성을 지킬 수 있었습니다.

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

동시성 문제를 해결하는 방법 중에는 격리 수준 설정과 비관적 락이 있습니다. 격리 수준 설정의 경우는 데이터 무결성이 가장 중요할 때 많이 사용되며, 비관적 락의 경우는 성능이 중요할 때 많이 사용됩니다. 만일 금융 관련 서비스 였다면 격리 수준 설정을 통해 문제를 해결하겠지만, 상품 구매 서비스의 경우에는 비관적 락을 사용했습니다. 이처럼 성능과 무결성 트레이드 오브 관계를 서비스의 따라 적용하면 될 것 같습니다.

비관적 락의 경우 JPA에서 지원하고 있으며 대표적으로 PESSIMISTIC_WRITE, PESSIMISTIC_READ 두 타입이 존재합니다. PESSIMISTIC_READ의 경우에는 ‘SELECT ... FOR SHARE’ 방식으로 동작하며 PESSIMISTIC_WRITE는 ‘SELECT ... FOR UPDATE’ 방식으로 동작합니다. 현재 서비스에서는 상품의 수량 데이터가 수정되기 때문에 PESSIMISTIC_WRITE를 사용하여 @Lock(LockModeType.PESSIMISTIC_WRITE)처럼 어노테이션 형태로 사용할 수 있습니다.

```java
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select p from Product p where p.id = :id")
    Optional<Product> findByIdPessimisticLock(@Param("id") Long id);
}
```
### 검색기능 향상

갓생 사이트에서는 일정 검색 기능이 있습니다. 해당 기능은 관계형 데이터베이스 기반으로 구현되어 있으며 ‘더 보기’ 방식 페이징 처리가 되어있습니다. 하지만 JMeter를 사용하여 성능테스트를 해본 결과 성능이 저조하다는 것을 확인하였습니다. 이러한 성능을 개선하기 위하여 다음과 같은 방안을 적용해보았습니다.

먼저 MySQL의 전문검색을 활용해보았습니다. 일반적인 조건절에서는 인덱스를 활용하여 성능을 개선할 수 있지만 해당 기능은 LIKE 연산을 사용하기 때문에 인덱스를 활용한 성능 개선이 어려워 전문검색을 활용하였고 그 결과 기존 성능의 2배 빠른 성능을 가져올 수 있었습니다. 

다음으로는 ElasticSearch를 활용하여 성능을 올려보았습니다. ElasticSearch는 대표적인 검색 엔진으로 역색인 방식과 샤드를 활용하여 성능이 뛰어나다는 장점이 있습니다. 또한 하나의 쿼리로 카운트까지 알 수 있는 장점이 있습니다. ElasticSearch을 적용함으로써 발생하는 MySQL과의 동기화 문제는 Logstash의 파이프라인을 활용하여 처리하였습니다. ElasticSearch을 적용한 후에 성능테스트를 해본 결과 기존보다 90배 빠른 성능을 가져왔습니다.

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

JMeter 성능 테스트 결과는 다음과 같습니다. (1000개의 요청을 동시에 한 경우입니다.)

**MySQL**

![](https://velog.velcdn.com/images/gcael/post/fc2db863-8a86-4b4d-afc8-7eeb8bf4a352/image.png)

**MySQL(전문검색)**

![](https://velog.velcdn.com/images/gcael/post/f42e2391-a4e3-49ec-b7af-51fd1026bdaa/image.png)

**Elasticsearch**

![](https://velog.velcdn.com/images/gcael/post/6b2cafd4-4f05-4d9e-84a9-2bb0f814b3a7/image.png)

전문검색을 활용하면 2배 가까이 ElasticSearch을 활용하면 90배 가까이 성능을 향샹시킬 수 있는 것을 확인할 수 있습니다.
