<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
</head>
<body>
<form action="/guestbook/list">
    <select name="type">
        <option value="">------</option>
        <option value="t" <c:if test = "${ type eq 't'}" > selected</c:if>>제목</option>
        <option value="tc" <c:if test = "${ type eq 'tc'}" > selected</c:if>>제목+내용</option>
        <option value="tcw" <c:if test = "${ type eq 'tcw'}" > selected</c:if>>제목+내용+작성자</option>
    </select>
    <input type="text" name="keyword" value="${keyword}">
    &nbsp;정렬
    <select name="sort">
        <option value="gno" <c:if test = "${ sort eq 'gno'}" > selected</c:if>>번호</option>
        <option value="title" <c:if test = "${ type eq 'title'}" > selected</c:if>>제목</option>
        <option value="regDate" <c:if test = "${ type eq 'regDate'}" > selected</c:if>>등록일자</option>
    </select>
    <input type="submit" value="검색">
</form>
<table border="1">
    <tr>
    <th>번호</th>
        <th>제목</th>
        <th>내용</th>
        <th>작성자</th>
        <th>등록일자</th>
        <th>수정일자</th>
    </tr>
    <c:forEach items="${result.getDtoList()}" var="guestbook">
        <tr>
            <th><a href="/guestbook/read?gno=${guestbook.getGno()}">${guestbook.getGno()}</a></th>
            <th>${guestbook.getTitle()}</th>
            <th>${guestbook.getContent()}</th>
            <th>${guestbook.getWriter()}</th>
            <th>${guestbook.getRegDate()}</th>
            <th>${guestbook.getModDate()}</th>
        </tr>
    </c:forEach>
</table>
<div class="paginate">
    <c:if test="${result.isPrev()}"><a href="/guestbook/list?keyword=${keyword}&type=${type}&page=${result.getStart() - 1}">이전</a></c:if>
    <span>
        <c:forEach items="${result.getPageList()}" var="page">
            <a href="/guestbook/list?keyword=${keyword}&type=${type}&page=${page}" <c:if test="${result.getPage() == page}">style="color: red" </c:if>>${page}</a>
        </c:forEach>
    </span>
    <c:if test="${result.isNext()}"><a href="/guestbook/list?keyword=${keyword}&type=${type}&page=${result.getEnd() + 1}">다음</a></c:if>
</div>
</body>
</html>