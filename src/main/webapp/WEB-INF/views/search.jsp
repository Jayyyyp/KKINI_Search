<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <style>
        #suggestionsBox {
            border: 1px solid #ccc;
            max-height: 150px;
            overflow-y: auto;
            position: absolute;
            width: calc(100% - 2px); /* minus border width */
        }
        .suggestion {
            padding: 8px;
            cursor: pointer;
        }
        .suggestion:hover {
            background-color: #f5f5f5;
        }
    </style>
    <title>Search</title>
    <!-- Add jQuery library -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>

<!-- 카테고리 선택 드롭다운 -->
<select id="categorySelect">
    <option value="KKINI">KKINI</option>
    <option value="KKINI_GREEN">KKINI Green</option>
</select>
<form action="/search" method="get" id="searchForm">
    <!-- 카테고리 선택 드롭다운 -->
    <select name="category" id="categorySelect">
        <option value="KKINI">KKINI</option>
        <option value="KKINI_GREEN">KKINI Green</option>
    </select>

    <!-- 검색창 -->
    <input type="text" name="query" id="searchInput" placeholder="Search...">

    <!-- 검색 버튼 -->
    <button type="submit">Search</button>
</form>

<!-- 검색창 -->
<input type="text" id="searchInput" placeholder="Search...">
<div id="suggestionsBox"></div>

<script type="text/javascript">
    $(document).ready(function() {
        $("#searchInput").on('input', function() {
            let query = $(this).val().trim();  // 공백 제거
            let category = $('#categorySelect').val();

            if(query) {  // query 값이 존재할 때만 API 호출
                $.get("/api/search/suggestions", { query: query, category: category }, function(data) {
                    let html = '';
                    data.forEach(function(item) {
                        html += '<div class="suggestion">' + item + '</div>';
                    });
                    $("#suggestionsBox").html(html);
                });
            } else {
                $("#suggestionsBox").html('');  // 입력이 없을 경우 suggestionBox 비우기
            }
        });
    });
</script>

</body>
</html>
