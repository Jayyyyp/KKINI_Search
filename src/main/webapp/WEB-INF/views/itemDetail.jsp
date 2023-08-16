<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css">
    <title>Item Detail</title>
    <style>
        /* 스타일 추가 */
        .item-info {
            border: 1px solid #e0e0e0;
            padding: 20px;
            margin-bottom: 20px;
        }

        .item-detail {
            border: 1px solid #e0e0e0;
            padding: 20px;
            height: 300px; /* 임의의 높이 설정 */
            overflow-y: auto; /* 스크롤 추가 */
        }
        .rating-display {
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
        #ratingText{
            height: 100px;
            width: 400px;
        }

        .fa-star-rating{
            color: #ccc; /* 기본 별 색상 */
            cursor: pointer;
        }
        .rating-list {
            max-height: 200px;  /* 원하는 높이로 설정 */
            overflow-y: auto;
        }
        .top-right-link {
            position: absolute;
            top: 10px;  /* 상단에서의 거리 */
            right: 10px;  /* 우측에서의 거리 */
            z-index: 100;  /* 다른 요소들 위에 위치하게 하는 속성 */
        }
        .rating-item {
            border-bottom: 1px solid #e0e0e0; /* 하단 경계선 추가 */
            padding: 10px 0; /* 상하 패딩 추가 */
        }

        .rating-item:last-child {
            border-bottom: none; /* 마지막 항목의 하단 경계선 제거 */
        }
    </style>
</head>
<body>
<h1>Item Detail</h1>
<a href="/items/search" class="top-right-link">Back to Search</a>
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
    <button onclick="location.href='/rate/write/${item.itemId}'" style="display: inline-block;">평점 작성하러가기</button>
    </p>
    <img src="${item.productImage}" alt="${item.name}" width="250">
</div>

<div id="userRatings"></div>

    <!--평점 목록-->
<div class="rating-list">
    <c:forEach items="${ratings}" var="rating">
        <div class="rating-item" data-user-id="${rating.users.userId}">
            <div>
                <p>${rating.ratingText}</p>
                <c:forEach begin="1" end="${rating.ratingValue}" var="star">
                    <span class="fa fa-star" style="color: gold;"></span>
                </c:forEach>
                <c:forEach begin="${rating.ratingValue + 1}" end="5" var="emptyStar">
                    <span class="fa fa-star" style="color: gray;"></span>
                </c:forEach>

                <!-- 수정 및 삭제 버튼을 항상 표시 -->
                <a href="/rate/edit/${rating.ratingId}">수정</a>
                <a href="/rate/delete/${rating.ratingId}">삭제</a>
            </div>
        </div>
    </c:forEach>
</div>

<!-- 상품 상세 정보 섹션 -->
    <div class="item-detail">
        <h2>Product Details</h2>
        <!-- 임의 내용 -->
        <p>상품 상세 설명임</p>
    </div>

    <script>
        document.addEventListener('DOMContentLoaded', function() {
            console.log("Document is fully loaded.");
            // 상품의 평점 표시
            setRatingDisplay();

            // 평점 입력
            const ratingInputStars = document.querySelectorAll('.rating-input .fa-star');
            ratingInputStars.forEach((star, index) => {
                star.addEventListener('click', function() {
                    document.querySelector(`#star${index + 1}`).checked = true;
                });
            });
        });

        function setRatingDisplay() {
            console.log("setRatingDisplay is called.");
            const ratingDisplay = document.querySelector('.rating-display');
            const displayRateValue = parseFloat(ratingDisplay.getAttribute('data-rating'));
            console.log("Display Rate Value:", displayRateValue);
            const fullStars = Math.floor(displayRateValue);
            const hasHalfStar = displayRateValue - fullStars >= 0.5;

            for (let i = 0; i < fullStars && i < ratingDisplay.children.length; i++) {
                ratingDisplay.children[i].classList.add('active-star');
            }
            if (hasHalfStar && fullStars < ratingDisplay.children.length) {
                ratingDisplay.children[fullStars].classList.add('half-star');
            }
        }
    </script>
</body>
</html>
