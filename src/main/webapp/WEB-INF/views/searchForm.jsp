<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>
</head>
<body>
<form action="/items/search" method="get">
    <select name="name">  <!-- 카테고리명으로 검색한다고 가정 -->
        <c:forEach var="category" items="${categories}">
            <option value="${category}">${category}</option>
        </c:forEach>
    </select>
    <input type="submit" value="Search">
</form>
</body>
</html>