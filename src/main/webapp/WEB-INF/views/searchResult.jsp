<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Search Results</title>
</head>
<body>
<h1>Search Results:</h1>
<table border="1">
    <tr>
        <th>ID</th>
        <th>Category</th>
        <th>Name</th>

    </tr>
    <c:forEach var="item" items="${items}">
        <tr>
            <td>${item.id}</td>
            <td>${item.category}</td>
            <td>${item.name}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>