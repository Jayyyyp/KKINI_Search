<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Item Search</title>
    <style>
        form {
            margin-bottom: 20px;
        }
    </style>
</head>
<body>

<h1>Search by Category</h1>
<form action="/items/search" method="get">
    <label for="category">Choose a category:</label>
    <select name="category" id="category">
        <option value="KKINI">KKINI</option>
        <option value="KKINI-Green">KKINI-Green</option>
    </select>
    <input type="submit" value="Search">
</form>

<h1>Search for Products by Name</h1>
<form action="/items/search" method="get">
    <input type="text" name="name" placeholder="Enter product name">
    <input type="submit" value="Search">
</form>
</body>
</html>
