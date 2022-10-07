package org.zerock.guestbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.zerock.guestbook.entity.Guestbook;

public interface GuestbookRepository extends JpaRepository<Guestbook, Long>, QuerydslPredicateExecutor<Guestbook> {
    // JpaRepository : JPA 에서 기본 지원하는 여러 종류의 동작
    // QuerydslPredicateExecutor : QueryDsl 구문을 통해 만들어진 조건을 통해 조회 가능
}
