<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
</head>
<body>
<form action="/guestbook/register" method="POST">
<table border="1">
    <tr>
    <td>제목 :  <input type="text" name="title"></td>
    </tr>
    <tr>
        <td>내용 :  <input type="text" name="content"></td>
    </tr>
    <tr>
        <td>작성자 :  <input type="text" name="writer"></td>
    </tr>
</table>
    <input type="submit" value="등록">
</form>
</body>
</html>