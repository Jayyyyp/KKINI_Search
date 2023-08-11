<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Item Search</title>
    <style>
        .autocomplete-items {
            border: 1px solid #d4d4d4;
            border-bottom: none;
            border-top: none;
            z-index: 99;
            max-height: 150px;
            overflow-y: auto;
            position: absolute;
            width: 100%;
            cursor: pointer;
        }
        .autocomplete-items div {
            padding: 10px;
            cursor: pointer;
            background-color: #fff;
            border-bottom: 1px solid #d4d4d4;
        }
        .autocomplete-items div:hover {
            background-color: #e9e9e9;
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
    <div id="autocomplete-dropdown" class="autocomplete-items"></div>
    <datalist id="recentSearches">
        <!-- JavaScript will dynamically insert recent searches here -->
    </datalist>

    <br>
    <input type="submit" value="Search">
</form>

<h3>Recent Searches</h3>
<ul id="recentSearchList"></ul>
<script>
    let recentSearches = JSON.parse(localStorage.getItem('recentSearches') || '[]');

    const searchForm = document.getElementById('searchForm');
    const searchInput = document.getElementById('searchInput');
    const recentSearchList = document.getElementById('recentSearchList');
    const dataList = document.getElementById('recentSearches');
    const autocompleteDropdown = document.getElementById("autocomplete-dropdown");

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
            span.style.cursor = "pointer"; // To show it's clickable
            span.onclick = function() { // Add this onclick event
                searchInput.value = term;
                searchForm.submit();
            };
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

    document.addEventListener("DOMContentLoaded", function() {
        searchInput.addEventListener("input", function(e) {
            var value = e.target.value;

            autocompleteDropdown.innerHTML = ""; // 드롭다운 내용을 초기화합니다.

            // 자동완성 목록을 보여주는 부분
            if (value) {
                fetch(`/items/search?name=${value}`)
                    .then(response => response.json())
                    .then(data => {
                        data.forEach(itemName => {
                            var div = document.createElement("div");
                            div.innerHTML = itemName;
                            div.addEventListener("click", function() {
                                searchInput.value = this.innerText;
                                autocompleteDropdown.innerHTML = "";
                            });
                            autocompleteDropdown.appendChild(div);
                        });

                        // 최근 검색어를 드롭다운에 추가하는 부분
                        if(recentSearches.length) {
                            let divider = document.createElement("div");
                            divider.innerHTML = "<hr><strong>Recent Searches</strong>";
                            autocompleteDropdown.appendChild(divider);

                            recentSearches.forEach(term => {
                                let div = document.createElement("div");
                                div.innerHTML = term;
                                div.addEventListener("click", function() {
                                    searchInput.value = this.innerText;
                                    autocompleteDropdown.innerHTML = "";
                                });
                                autocompleteDropdown.appendChild(div);
                            });
                        }
                    });
            } else { // 입력 값이 없을 때는 최근 검색어만 보여줍니다.
                recentSearches.forEach(term => {
                    let div = document.createElement("div");
                    div.innerHTML = term;
                    div.addEventListener("click", function() {
                        searchInput.value = this.innerText;
                        autocompleteDropdown.innerHTML = "";
                    });
                    autocompleteDropdown.appendChild(div);
                });
            }
        });
    });
</script>
</body>
</html>
