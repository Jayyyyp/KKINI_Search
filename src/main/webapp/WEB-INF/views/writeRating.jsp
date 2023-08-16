<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css">
    <title>Write a Rating</title>
    <style>
        .item-info {
            border: 1px solid #e0e0e0;
            padding: 20px;
            margin-bottom: 20px;
        }
        .rating-input {
            display: flex;
            align-items: center;
            gap: 5px;
        }

        .rating-input .fa-star {
            color: #ccc; /* 기본 별 색상 */
            cursor: pointer;
        }
        .rating-input .fa-star.active-star {
            color: gold; /* 활성화된 별 색상 */
        }
    </style>
</head>
<body>
<!-- 상품 기본 정보 섹션 -->
<div class="item-info">
    <h2>Product Information</h2>
    <p><strong>Category: </strong> ${item.category.categoryName}</p>
    <p><strong>Name: </strong> ${item.name}</p>
    <p>
        <strong>평점: </strong>
    <div class="rating-display" data-rating="${item.averageRating}" style="display: inline-block;">
        <span class="fa fa-star"></span>
    </div>
    ${item.averageRating}
    </p>
    <img src="${item.productImage}" alt="${item.name}" width="250">
</div>

<!--상품 평점 입력 섹션 -->
<h1>Write a Rating for Item</h1>

<form action="/rate/rating/${itemId}" method="post">
    <label for="userId">User ID:</label>
    <input type="number" id="userId" name="userId" required>
    <br><br>
    <label for="ratingValue">Rating Value (1-5):</label>
    <div class="rating-input">
        <c:forEach var="i" begin="1" end="5">
            <span class="fa fa-star" data-value="${i}"></span>
        </c:forEach>
        <input type="number" id="ratingValue" name="ratingValue" min="1" max="5" required hidden>
    </div>
    <br><br>

    <label for="ratingText">Rating Text:</label>
    <textarea name="ratingText" rows="4" cols="50" required></textarea><br><br>

    <!-- 임시 사용자 ID -->
    <input type="hidden" name="userId" value="${userId}">

    <input type="submit" value="Submit Rating">
</form>
<script>
    document.addEventListener('DOMContentLoaded', function() {
        const ratingStars = document.querySelectorAll('.rating-input .fa-star');
        ratingStars.forEach((star, index) => {
            star.addEventListener('click', function() {
                resetStars();  // reset previous selection
                for(let i=0; i<=index; i++) {
                    ratingStars[i].classList.add('active-star');
                }
                document.getElementById('ratingValue').value = index + 1;
            });
        });
        setItemRatingDisplay();
    });

    function setItemRatingDisplay() {
        const ratingDisplay = document.querySelector('.item-info .rating-display');
        const displayRateValue = parseFloat(ratingDisplay.getAttribute('data-rating'));  // 변경된 부분
        const fullStars = Math.floor(displayRateValue);

        for (let i = 0; i < fullStars; i++) {
            ratingDisplay.children[i].classList.add('active-star');
        }
    }

    function resetStars() {
        const ratingStars = document.querySelectorAll('.rating-input .fa-star');
        ratingStars.forEach(star => {
            star.classList.remove('active-star');
        });
    }
</script>

</body>
</html>