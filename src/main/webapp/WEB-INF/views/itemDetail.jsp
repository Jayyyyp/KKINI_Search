<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
            color: #ccc;
        }
        .active-star {
            color: gold;
        }
        .partial-fill {
            background-color: transparent;
            position: relative;
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
    <fmt:formatNumber value="${item.averageRating}" pattern="#.##"/>
    <button onclick="location.href='/rate/write/${item.itemId}'" style="display: inline-block;">평점 작성하러가기</button>
    </p>
    <img src="${item.productImage}" alt="${item.name}" width="250">
</div>

<div class="search-section">
    <input type="text" id="userIdSearch" placeholder="Enter userId to search">
    <button onclick="searchByUserId()">Search by userId</button>
    <button onclick="showAllRatings()">Show All</button>
</div>

<div id="userRatings"></div>
<!--평점 목록-->
<div class="rating-list">
    <c:forEach items="${ratings}" var="rating" varStatus="status">
        <div class="rating-item" data-user-id="${rating.users.userId}">
            <div>
                <p>${rating.ratingText}</p>

                <!-- 평점 이미지 표시 -->

                <c:if test="${not empty rating.ratingImage1}">
                    <img src="/items${rating.ratingImage1}" alt="Rating Image 1" width="100">
                </c:if>
                <c:if test="${not empty rating.ratingImage2}">
                    <img src="/items${rating.ratingImage2}" alt="Rating Image 2" width="100">
                </c:if>
                <c:if test="${not empty rating.ratingImage3}">
                    <img src="/items${rating.ratingImage3}" alt="Rating Image 3" width="100">
                </c:if>
                <c:if test="${not empty rating.ratingImage4}">
                    <img src="/items${rating.ratingImage4}" alt="Rating Image 4" width="100">
                </c:if>

                <!-- 평점 별점 표시 -->
                <c:forEach varStatus="starStatus" begin="1" end="5">
                    <c:choose>
                        <c:when test="${starStatus.index <= rating.ratingValue}">
                            <span class="fa fa-star" style="color: gold;"></span>
                        </c:when>
                        <c:otherwise>
                            <span class="fa fa-star" style="color: gray;"></span>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
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
        const ratingDisplay = document.querySelector('.rating-display');
        const displayRateValue = parseFloat(ratingDisplay.getAttribute('data-rating'));
        const fullStars = Math.floor(displayRateValue);
        const hasHalfStar = displayRateValue - fullStars >= 0.5;

        for (let i = 0; i < fullStars; i++) {
            if (ratingDisplay.children[i]) {
                ratingDisplay.children[i].classList.add('active-star');
            }
        }
    }

    function searchByUserId() {
        const userId = document.getElementById('userIdSearch').value.toString();
        const ratingItems = document.querySelectorAll('.rating-item');

        ratingItems.forEach(item => {
            item.style.display = (item.getAttribute('data-user-id') === userId) ? 'block' : 'none';
        });
    }

    function showAllRatings() {
        document.querySelectorAll('.rating-item').forEach(item => item.style.display = 'block');
    }

    document.addEventListener('DOMContentLoaded', function() {
        setRatingDisplay();

    });
    function previewImage(event, index) {
        var reader = new FileReader();
        reader.onload = function() {
            // 이미지 미리보기에 데이터 URL 설정
            var output = document.getElementById('imagePreview' + index);
            output.src = reader.result;
        };
        reader.readAsDataURL(event.target.files[0]);
    }
</script>
</body>
</html>