# Part2 Spring MVC/JPA/Thymeleaf 연습(방명록 구현)

## 환경
* Spring boot : 2.6.4
  * Thymeleaf(동적 화면 구성)
  * lombok(어노테이션 기반 코드 다이어트 라이브러리)
  * Spring Data JPA(자바 진영의 ORM 표준 인터페이스 모음)
  * Spring Web(Embedded WAS 등 웹 구현에 필요한 라이브러리 모음)
  * Spring Boot DevTools(boot 개발 편의를 위한 모듈)  
* DB : mariaDB 10

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

* 수정/삭제 시 POST 를 사용하는 이유?
  * POST 요청 : 서버에서 보낸 데이터를 대상 리소스가 처리하도록 하는 요청의 의미도 있음
  * Method 에는 POST 를 채용하고, URI 에 action 을 하는 형태로도 사용한다
  
* 계층 구분

![hierachy](./picture/hierachy.PNG)

* 계층을 나누는 이유
  * MVC 패턴(Model, View, Controller)
  * Controller : 요청/응답 담당
  * Service : 요청/응답에 대해서 로직 처리와 data 계층을 이어주는 레이어
  * Repository : Data 처리 담당 레이어
  * 컨트롤러는 서비스를 여러개 사용할 수 있고, 서비스는 Repository 를 여러개 사용할 수 있다(코드 중복 제거)

* DTO 를 사용하는 이유
  * view 단에서 Domain 을 사용하게 되면 불필요한 정보가 노출 될 수 있음 
  * 뷰의 요구사항 변화로 Domain 클래스를 변경하지 않을 수 있음
  
## JPA(Java Persistence API)
* ORM(Object Relation Mapping)
  * 테이블 <-> 클래스가 유사한 부분이 있음(회원, 주소.. )
  * RDBMS <-> 객체 간 변환을 지원함

* JPA
  * ORM 을 Java 언어에 맞게 사용하기 위한 스펙, ORM 의 하위 개념
  * 아래와 같은 구조
    
  ![JPA](./picture/JPA.PNG)
  
  * 특징
    * Persistence :

  
  