<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
</head>
<body>
<table border="1">
    <tr><td>번호 : ${dto.getGno()}</td></tr>
    <tr><td>제목 : ${dto.getTitle()}</td></tr>
    <tr><td>내용 : ${dto.getContent()}</td></tr>
    <tr><td>작성자 : ${dto.getWriter()}</td></tr>
    <tr><td>등록일자 : ${dto.getRegDate()}</td></tr>
    <tr><td>수정일자 : ${dto.getModDate()}</td></tr>
</table>
<a href="/guestbook/modify?gno=${dto.getGno()}">수정</a>
<form action="/guestbook/remove" METHOD="post">
    <input type="hidden" name="gno" value="${dto.getGno()}">
    <input type="submit" value="삭제">
</form>
</body>
</html>