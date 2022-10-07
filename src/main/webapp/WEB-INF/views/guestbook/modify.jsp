<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
</head>
<body>
<form action="/guestbook/modify" method="POST">
        <input type="hidden" name="gno" value="${dto.getGno()}">
    <table border="1">
        <tr>
            <td>제목 :  <input type="text" name="title" value="${dto.getTitle()}"></td>
        </tr>
        <tr>
            <td>내용 :  <input type="text" name="content" value="${dto.getContent()}"></td>
        </tr>
        <tr>
            <td>작성자 :  ${dto.getWriter()}</td>
        </tr>
        <tr>
            <td>등록일자 :  ${dto.getRegDate()}</td>
        </tr>
        <tr>
            <td>수정일자 :  ${dto.getModDate()}</td>
        </tr>
    </table>
    <input type="submit" value="수정">
</form>
</body>
</html>