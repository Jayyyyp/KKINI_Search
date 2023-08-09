<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Item Search</title>
</head>
<body>
<form action="/items/search" method="post" id="searchForm">

    <!-- 카테고리 선택 드롭다운 -->
    <select name="category" id="categorySelect">
        <option value="KKINI">KKINI</option>
        <option value="KKINI_GREEN">KKINI Green</option>
    </select>

    <!-- 검색창 -->
    Search: <input type="text" name="name" id="searchInput" placeholder="Search...">

    <!-- 검색 버튼 -->
    <input type="submit" value="Search" />

</form>

</form>
</body>
</html>