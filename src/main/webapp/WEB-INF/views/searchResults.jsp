<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css">
    <meta charset="UTF-8">
    <title>Search Results</title>
    <style>
        .item-row:hover {
            background-color: darkgray;
        }

        .item-name-link {
            text-decoration: none;  /* 링크 밑줄 제거 */
            color: inherit;  /* 링크 색상을 상속받음 */
            transition: color 0.3s;  /* 색상 변화 애니메이션 */
        }
        .item-name-link:hover {
            color: blue;  /* 호버 시 색상 변경 */
        }

        .fa-star {
            color: #ccc; /* 기본 별 색상 */
        }
        .active-star {
            color: gold; /* 활성화된 별 색상 */
        }

        .partial-fill {
            background-color: transparent; /* 배경색을 투명하게 설정 */
            position: relative;
        }
        .half-star::before {
            content: '\f089'; /* 반 별 모양 */
            font-family: "Font Awesome 5 Free";
            font-weight: 900;
            color: gold;
        }
    </style>
</head>
<body>

<h1>Search Results</h1>
<a href="/items/search">Back to Search Page</a>

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
        <tr class="item-row">
            <td>
                <a href="/items/${item.itemId}" class="item-name-link">${item.name}</a>
            </td>
            <td>
                <div class="rating" data-rating="${item.averageRating}">
                    <span class="fa fa-star"></span>
                    <span class="fa fa-star"></span>
                    <span class="fa fa-star"></span>
                    <span class="fa fa-star"></span>
                    <span class="fa fa-star"></span>
                </div>
            </td>
            <td><img src="${item.productImage}" alt="${item.name}" width="100"></td>
            <td>${item.lowestPrice}</td>
            <td>${item.category.categoryName}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<script>
    document.addEventListener('DOMContentLoaded', function() {
        const ratings = document.querySelectorAll('.rating');
        ratings.forEach(rating => {
            let rateValue = parseFloat(rating.getAttribute('data-rating'));

            // 소수점 두 번째 자리에서 반올림
            rateValue = Math.round(rateValue * 10) / 10;

            const fullStars = Math.floor(rateValue);
            const partialFill = rateValue - fullStars;

            for (let i = 0; i < fullStars; i++) {
                rating.children[i].classList.add('active-star');
            }

            if (partialFill >= 0.5) {
                const halfStar = rating.children[fullStars];
                halfStar.classList.add('half-star');
            }
        });
    });
</script>
</body>
</html>
