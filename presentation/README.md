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
  |수정|/guestbook/modify|GET|조회 화면|

