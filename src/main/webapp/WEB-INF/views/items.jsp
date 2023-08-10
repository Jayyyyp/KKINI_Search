<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Items by Category</title>
</head>
<body>
<h2>Items List</h2>

<c:if test="${empty items}">
    <p>No items available for the selected category.</p>
</c:if>

<c:if test="${not empty items}">
    <table border="1">
        <thead>
        <tr>
            <th>ID</th>
            <th>Category ID</th>
            <th>Name</th>
            <th>Average Rating</th>
            <th>Product Image</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="item" items="${items}">
            <tr>
                <td><c:out value="${item.id}"/></td>
                <td><c:out value="${item.category.categoryId}"/></td>
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
