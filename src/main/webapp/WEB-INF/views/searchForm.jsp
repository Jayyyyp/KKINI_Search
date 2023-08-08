<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Item Search</title>
</head>
<body>
<form action="/items/search" method="post">
    Search: <input type="text" name="name" />
    <input type="submit" value="Search" />
</form>
</body>
</html>