<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Search Results</title>
</head>
<body>

<h1>Search Results</h1>

<table border="1">
    <thead>
    <tr>
        <th>Item Name</th>
        <th>Average Rating</th>
        <th>Product Image</th>
        <th>Lowest Price</th>
        <th>Category</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="item" items="${items}">
        <tr>
            <td>${item.name}</td>
            <td>${item.averageRating}</td>
            <td><img src="${item.productImage}" alt="${item.name}" width="100"></td>
            <td>${item.lowestPrice}</td>
            <td>${item.category.categoryName}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<a href="/items/search">Back to Search Page</a>

</body>
</html>
