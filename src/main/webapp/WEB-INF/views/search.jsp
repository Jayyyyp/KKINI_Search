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
<input type="text" id="searchInput" placeholder="Search...">
<div id="suggestionsBox"></div>

<script type="text/javascript">
    $(document).ready(function() {
        $("#searchInput").on('input', function() {
            let query = $(this).val();
            $.get("/api/search/suggestions", { query: query }, function(data) {
                let html = '';
                data.forEach(function(item) {
                    html += '<div class="suggestion">' + item + '</div>';
                });
                $("#suggestionsBox").html(html);
            });
        });

        $("#suggestionsBox").on('click', '.suggestion', function() {
            let selected = $(this).text();
            $('#searchInput').val(selected);  // Fill the search input with the selected item
            $("#suggestionsBox").html('');   // Clear suggestions box
            // Optional: If you want to redirect to another page based on the selected item
            // window.location.href = "/search?query=" + selected;
        });
    });
</script>
</body>
</html>






