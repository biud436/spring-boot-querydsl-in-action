# 소개

스프링 부트를 이용한 REST API 서버

# 기술 스택

- Spring Boot
- Spring Data JPA
- Spring Data Redis
- Spring Security
- QueryDSL

# 2023.03.26
- 스프링 시큐리티 추가
  - UserDetails 과 UserDetailsService 구현이 필요하였음
  - 하이버네이트의 테이블 생성 버그로 인하여 ROLE 컬럼을 `@ElementCollection`이 아닌 다른 방법으로 매핑해야 함을 느꼈음. Node의 TypeORM과 달리 sync 기능이 제대로 작동하지 않고 create 시 데이터가 전부 날아가므로 DDL로 수동 생성하는 것이 적합해보임.
  - 스프링 시큐리티는 자바 서블렛 필터를 고도화한 것으로, 디버깅 결과 수 많은 필터가 체인으로 연결되어 실행됨을 확인하였음. 도대체 어디에서 문제가 생긴건지 제대로 파악이 힘들 정도로 많은 메소드가 실행됨. 따라서 필터 순서에 따라 처리가 되지 않아 제대로되는 필터를 알아내는데 애먹었음.
  - 제대로된 참고 문서가 없고, 8만원 짜리 유료 강좌를 구매해도 버전이 달라 학습에 애를 먹었음. 공식 문서에서도 찾기가 힘들었음
  - 디버깅 기능이 상당히 매력 있었음.
  - 작동 과정이 꽤나 복잡하여 수 많은 블로그 글들도 누가 시니어인지 주니어인지 설명이 전부 달랐고 제대로 동작하지 않는 경우도 있었음.
  - DevToy의 JWT 디코딩 기능이 매우 유용했음
  - 정확한 래퍼런스를 찾기 위해 강의 구매 등 여러가지 고수들의 자료를 참고해야 함을 느낌.
  - 디코딩된 토큰의 roles 값에 ROLE_USER, ROLE_ADMIN 등이 있어야 함.
  - 리액트의 Context처럼, 컨텍스트에 현재 인증 상태를 저장해둬야 함. 이때 Authentication이란 걸 생성하여 전달해야 함.
  - 기본 오류는 보안 문제로 오류 메시지가 응답으로 출력되지 않는데 ControllerAdvice와 ExceptionHandler로 캐치하면 오류를 반환할 수 있음

# 2023.10.09

- 인증 정보를 매개변수에 자동 주입
  - HandlerMethodArgumentResolver를 이용하여 스프링 시큐리티의 인증 정보를 유저 정보로 변환하는 처리를 하였다. `@UserInfo` 어노테이션이 있을 경우, 유저 정보가 매개변수로 주입된다. HandlerMethodArgumentResolver는 NestJS의 커스텀 매개변수 데코레이터를 생성하는 방법보다 다양한 설정을 할 수 있었다. 
  - `@Aspect`와 `@Around`를 이용하여 `@LogTime`어노테이션을 만들었다. 또한 이를 이용하여 스프링 시큐리티의 인증 정보를 가져와 `@UserOnly` 어노테이션을 생성하였다. NestJS보다 강력한 AOP 기능을 제공한다.

- 트랜잭션 전파
  - 트랜잭션 전파 속성과 readOnly를 추가하였다. 다만 실제 물리 트랜잭션 로우 쿼리를 제대로 확인할 수 없는 불편함이 있었다. 

# 2023.10.12

- 패키지 구조 변경
  - 범용적인 구조로 변경

# 2023.10.13

- 더 효율적인 AOP 처리로 변경
  - `@LogTime`을 ProxyFactory로 만든 LoggingTimeProxyHandler로 대신 처리  

# 2023.10.15

 - `@CollectionTable`를 추가하여 ROLE 컬럼 매핑과 테이블명을 변경
 - 하이버네이트 관련 문제 수정
   - DDL 자동 생성 시, 일부 테이블 drop 오류 -> DB 재시작하여 해결
   - 자동 생성 기능 사용 자제

# 2023.10.16

- AWS SES를 이용하여 메일 보내는 기능
- Spring Data Redis를 이용하여 레디스(Redis)를 추가하고 값을 쓰고 읽기