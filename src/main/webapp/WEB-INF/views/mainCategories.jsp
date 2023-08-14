<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Main Categories</title>
</head>
<body>
<h2>Select a Main Category</h2>

<c:forEach var="category" items="${mainCategories}">
    <a href="/category/${category.categoryId}">${category.categoryName}</a><br>
</c:forEach>

<h2>All Items</h2>
<c:if test="${empty items}">
    <p>No items available for the selected category.</p>
</c:if>

<c:if test="${not empty items}">
    <table border="1">
        <thead>
        <tr>
            <th>ID</th>
            <th>Category Name</th>
            <th>Name</th>
            <th>Average Rating</th>
            <th>Product Image</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="item" items="${items}">
            <tr>
                <td><c:out value="${item.id}"/></td>
                <td><c:out value="${item.category.categoryName}"/></td>
                <td><c:out value="${item.name}"/></td>
                <td><c:out value="${item.averageRating}"/></td>
                <td>
                    <c:if test="${not empty item.productImage}">
                        <img src="<c:out value='${item.productImage}'/>" alt="<c:out value='${item.name}'/>" width="100">
                    </c:if>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>
</body>
</html>