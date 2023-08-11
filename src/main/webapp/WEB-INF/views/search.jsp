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
        #recentSearchList {
            margin-top: 10px;
            list-style-type: none;
        }
        .recent-search-item {
            display: flex;
            align-items: center;
        }
        .remove-btn {
            margin-left: 10px;
            cursor: pointer;
        }
    </style>
</head>
<body>

<h1>Search for Products</h1>
<form action="/items/search" method="get" id="searchForm">

    <label for="category">Choose a category:</label>
    <select name="categoryId" id="category">
        <option value="1">KKINI</option>
        <option value="2">KKINI-Green</option>
    </select>
    <br>

    <input type="text" name="name" id="searchInput" placeholder="Enter product name" list="recentSearches">
    <datalist id="recentSearches">
        <!-- JavaScript will dynamically insert recent searches here -->
    </datalist>

    <br>
    <input type="submit" value="Search">
</form>

<h3>Recent Searches</h3>
<ul id="recentSearchList">
    <!-- JavaScript will dynamically insert recent searches here -->
</ul>
<script>
    let recentSearches = JSON.parse(localStorage.getItem('recentSearches') || '[]');

    const searchForm = document.getElementById('searchForm');
    const searchInput = document.getElementById('searchInput');
    const recentSearchList = document.getElementById('recentSearchList');
    const dataList = document.getElementById('recentSearches');

    searchForm.addEventListener('submit', function(event) {
        const searchTerm = searchInput.value.trim();
        if (searchTerm && !recentSearches.includes(searchTerm)) {
            recentSearches.push(searchTerm);
            localStorage.setItem('recentSearches', JSON.stringify(recentSearches));
            renderRecentSearches();
        }
    });

    function renderRecentSearches() {
        dataList.innerHTML = '';
        recentSearchList.innerHTML = '';
        recentSearches.forEach(term => {
            // Add to datalist for dropdown
            let option = document.createElement('option');
            option.value = term;
            dataList.appendChild(option);

            // Add to recent searches list with delete button
            let li = document.createElement('li');
            li.className = 'recent-search-item';

            let span = document.createElement('span');
            span.innerText = term;
            li.appendChild(span);

            let removeBtn = document.createElement('button');
            removeBtn.innerText = 'Delete';
            removeBtn.className = 'remove-btn';
            removeBtn.onclick = function() {
                recentSearches = recentSearches.filter(search => search !== term);
                localStorage.setItem('recentSearches', JSON.stringify(recentSearches));
                renderRecentSearches();
            };
            li.appendChild(removeBtn);

            recentSearchList.appendChild(li);
        });
    }
    renderRecentSearches();
</script>
</body>
</html>
