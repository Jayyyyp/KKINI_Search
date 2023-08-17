<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Rating</title>
</head>
<body>
<h1>Edit your rating</h1>
<form action="<%= request.getContextPath() %>/rate/edit/${rating.ratingId}" method="post">
    <div>
        <label for="ratingValue">Rating Value:</label>
        <input type="number" id="ratingValue" name="ratingValue" value="${rating.ratingValue}" required>
    </div>
    <div>
        <label for="ratingText">Rating Text:</label>
        <textarea id="ratingText" name="ratingText" rows="4" cols="50" required>${rating.ratingText}</textarea>
    </div>
    <button type="submit">Update Rating</button>
</form>
<br>
<a href="<%= request.getContextPath() %>/items/${rating.itemId}">Back to item</a>
</body>
</html>