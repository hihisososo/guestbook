# Part2 Spring MVC/JPA/Thymeleaf 연습(방명록 구현)

## 환경
* Spring boot : 2.6.4
  * lombok(어노테이션 기반 코드 다이어트 라이브러리)
  * Spring Data JPA(자바 ORM 인터페이스)
  * Spring Web(Embedded WAS 등 웹 구현에 필요한 라이브러리 모음)
  * Spring Boot DevTools(boot 개발 편의를 위한 모듈)
  * jsp 화면 구성
* DB : mariaDB 10

* Spring 프레임워크를 사용하는 이유?
  * Spring 프레임워크는 객체간의 의존성 주입을 설정 파일을 통해 유연하게 설정할 수 있다
    * ex) 테스트 시에만 사용하는 xml 설정을 통해, 코드의 수정 없이 유연한 테스트가 가능함
  * Application Context 에서, Bean으로 등록된 객체를 싱글톤으로 관리해주므로 사용자가 직접 구현할 필요가 없다
  * 트랜잭션의 간편함
    * 스프링 에서 지원해주는 부분 없이 직접 트랜잭션을 처리한다면 굉장한 소요 시간이 걸림
  * 결과적으로 비즈니스 로직 구현에만 집중할 수 있는 환경을 제공해준다

* Spring boot 를 사용하는 이유?
  * 자주 사용되는 라이브러리들의 버전 관리 자동화
    * spring-boot-jpa 한줄이면, 의존성 라이브러리들이 알아서 해결됨
  * 내장 웹서버 제공
  * 실행 가능한 jar

## 프로젝트 구조
* 구현할 URL
  
  |기능|URL|GET/POST|기능|
  |------|---|---|---|
  |목록|/guestbook/list|GET|목록/페이징/검색|
  |등록|/guestbook/register|GET|입력 화면|
  |등록|/guestbook/register|POST|등록 처리|
  |조회|/guestbook/read|GET|조회 화면|
  |수정|/guestbook/modify|GET|수정/삭제 가능 화면|
  |수정|/guestbook/modify|POST|수정 처리|
  |삭제|/guestbook/remove|POST|삭제 처리|

* 수정/삭제 시 UPDATE, DELETE 가 아닌 POST 를 사용하는 이유?
  * POST 요청 : 서버에서 보낸 데이터를 대상 리소스가 처리하도록 하는 요청의 의미도 있음
  * Method 에는 POST 를 채용하고, URI 에 action 을 넣어서 사용하는 형태로도 사용한다
  
* 계층 구분

![hierachy](./picture/hierachy.PNG)

* 계층을 나누는 이유
  * MVC 패턴(Model, View, Controller)
  * Controller : 요청/응답 담당 Model <-> View 를 연결해줌
  * Service(Model) : 요청/응답에 대해서 로직 처리와 data 계층을 이어주는 레이어
  * Repository : Data 처리 담당 레이어
  * ex) 만약에 컨트롤러에서 모든 로직을 처리하여 웹으로 개발된 어플리케이션을 모바일 버전으로 개발해야 한다면? 소스의 복잡성 때문에 굉장히 힘들 것, 하지만 계층이 잘 나뉘어져 있다면 서비스와 Repository 는 재사용하고 View 단만 바꿔주면 된다.  

* DTO 를 사용하는 이유
  * view 단에서 Domain 을 사용하게 되면 불필요한 정보가 노출 될 수 있음 
  * 뷰의 요구사항 변화로 Domain 클래스를 변경하지 않을 수 있음
  
## JPA(Java Persistence API)
* Persistence(영속성)
  * 데이터를 생성한 프로그램이 종료되더라도 사라지지 않는 데이터의 특성
  * 영속성을 가지게 되려면? 파일, DB 등 어딘가에 저장되어야 함
* Persistence Layer
  * 애플리케이션에서, 데이터에 영속성을 부여해주는 계층
  * JDBC 등을 활용할 수 있지만 Persistence Framework 를 많이 이용함 
* Persistence Framework
  * 간단한 작업만으로 DB 와 연동되는 시스템을 빠르게 개발
  * JPA, Mybatis 등


* ORM(Object Relation Mapping)
  * 객체와 관계형 데이터베이스의 데이터를 자동으로 매핑(연결)해주는 것
  * 객체는 객체지향 모델이고, 테이블은 관계형모델이므로 불일치가 존재하는데, 이 부분을 객체관의 관계를 바탕으로 SQL을 자동으로 생성하여 해결한다

* JPA
  * ORM 의 하위 개념으로 ORM Java 언어에 맞게 사용하기 위한 스펙
  * 장점
    * DB 변경이 용이하다
    * 객체에만 신경을 써서 코딩할 수 있다

  * 단점
    * 잘못 설계된 경우, 속도 저하
    * 성능이 중요하거나 복잡한 쿼리는 결국엔 SQL 을 사용해야 할 수 있음
    
  * 아래와 같은 구조
    
  ![JPA](./picture/JPA.PNG)

  * Entity Manager
    * Entity를 저장하고 수정하고 삭제하고 조회하는 등 Entity와 관련된 모든일을 수행
    * Thread-Safe 하지 않음 멀티쓰레드 환경에서, EntityManagerFactory 를 통해 EntityManager 가 만들어진다
  * Persistence Context  
    * Entity 를 영구 저장하는 환경, DB 에 바로 접근하지 않고 해당 컨텍스트에 엔티티가 보관된다. 주기적으로 flush() 되어 DB에 저장됨 
    * 어플리케이션과 DB 사이에서 반영될 객체를 보관
    * 사용 이유
      1. 1차 캐시 역할 : 조회 시, 먼저 Persistence Context 에 조회한 후 없으면 DB 조회
      2. 객체 동일성 보장 : 같은 ID 를 가진 객체가 동일한 인스턴스를 보장
      3. 트랜잭션 지원하는 쓰기 지연 
      4. 변경 감지 : 엔티티 변화를 자동으로 감지한 후 DB 에 반영

## QueryDSL 라이브러리
  * 사용 이유 
    1. JPA 쿼리 메서드 및 @Query 를 통해서는 복잡한 조합의 조건 질의가 힘들기 때문
    2. 문자가 아닌 코드로 쿼리를 작성함으로써, 컴파일 시점에 문법 오류를 쉽게 확인할 수 있다.
    2. 자동 완성 등 IDE의 도움을 받을 수 있다. 
    3. 동적인 쿼리 작성이 편리하다.
  
  * QueryDSL 사용 예
  
  ```java
  //검색 조건을 작성하기
          BooleanBuilder conditionBuilder = new BooleanBuilder();
  
          if(type.contains("t")){
          conditionBuilder.or(qGuestbook.title.contains(keyword));
          }
          if(type.contains("c")){
          conditionBuilder.or(qGuestbook.content.contains(keyword));
          }
          if(type.contains("w")){
          conditionBuilder.or(qGuestbook.writer.contains(keyword));
          }
  
          //모든 조건 통합
          booleanBuilder.and(conditionBuilder);
  ```

## 백엔드 구현 내용 설명
* 소스 보면서 같이..
  
