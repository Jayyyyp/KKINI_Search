<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
        .active-star {
            color: gold; /* 활성화된 별 색상 */
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
    <h2>상품 정보</h2>
    <p><strong>카테고리: </strong> ${item.category.categoryName}</p>
    <p><strong>이름: </strong> ${item.name}</p>
    <p>
        <strong>평점: </strong>
    <div class="rating-display" data-rating="${item.averageRating}" style="display: inline-block;">
        <span class="fa fa-star"></span>
    </div>
    <fmt:formatNumber value="${item.averageRating}" pattern="#.##"/>
    </p>
    <img src="${item.productImage}" alt="${item.name}" width="250">
</div>

<!--상품 평점 입력 섹션 -->
<h1>Write a Rating for Item</h1>

<form action="/rate/rating/${itemId}" method="post" enctype="multipart/form-data">
    <label for="userId">User ID:</label>
    <input type="text" id="userId" name="userId" required>  <!-- 사용자에게 userId를 입력받는 input 필드 -->
    <br><br>

    <label for="ratingValue">Rating Value (1-5):</label>
    <div class="rating-input">
        <c:forEach var="i" begin="1" end="5">
            <span class="fa fa-star" data-value="${i}"></span>
        </c:forEach>
        <input type="number" id="ratingValue" name="ratingValue" min="1" max="5" required hidden>
    </div>
    <br><br>

    <label for="ratingImage">Upload Rating Images:</label>
    <!-- 평점 이미지 표시 -->
    <!-- 이미지 선택 입력 필드 -->
    <input type="file" id="imageInput" name="ratingImages" onchange="previewImage(event)" multiple>

    <!-- 이미지 미리보기 -->
    <div id="imagePreviews">
        <div class="img-container">
            <img id="imagePreview1" width="100">
            <button onclick="removeImage(event, 'imagePreview1')">-</button>
        </div>
        <div class="img-container">
            <img id="imagePreview2" width="100">
            <button onclick="removeImage(event, 'imagePreview2')">-</button>
        </div>
        <div class="img-container">
            <img id="imagePreview3" width="100">
            <button onclick="removeImage(event, 'imagePreview3')">-</button>
        </div>
        <div class="img-container">
            <img id="imagePreview4" width="100">
            <button onclick="removeImage(event, 'imagePreview4')">-</button>
        </div>
    </div>

    <label for="ratingText">Rating Text:</label>
    <textarea name="ratingText" rows="4" cols="50" required></textarea><br><br>
    <input type="submit" value="Submit Rating">
</form>

<script>
    // 이미지 저장할 배열
    let selectedImages = [];
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

            document.querySelector('form').addEventListener('submit', function(event) {
                const dataTransfer = new DataTransfer();
                selectedImages.forEach(img => dataTransfer.items.add(img));
                document.getElementById('imageInput').files = dataTransfer.files;
            });
        });

        function setItemRatingDisplay() {
            const ratingDisplay = document.querySelector('.item-info .rating-display');
            const displayRateValue = parseFloat(ratingDisplay.getAttribute('data-rating'));  // 변경된 부분
            const fullStars = Math.floor(displayRateValue);

            for (let i = 0; i < fullStars && i < ratingDisplay.children.length; i++) {
                ratingDisplay.children[i].classList.add('active-star');
            }
        }

        function resetStars() {
            const ratingStars = document.querySelectorAll('.rating-input .fa-star');
            ratingStars.forEach(star => {
                star.classList.remove('active-star');
            });
        }
    function previewImage(event) {
        const files = event.target.files;
        for(let i = 0; i < files.length; i++) {
            selectedImages.push(files[i]);
            var reader = new FileReader();
            reader.onload = function(e) {
                document.getElementById('imagePreview' + (i + 1)).src = e.target.result;
            };
            reader.readAsDataURL(files[i]);
        }
    }

    function removeImage(event, imageId) {
        event.preventDefault();
        var imageIndex = parseInt(imageId.replace('imagePreview', '')) - 1;
        if (selectedImages[imageIndex]) {
            selectedImages.splice(imageIndex, 1);
        }
        var imageElement = document.getElementById(imageId);
        if (imageElement) {
            imageElement.src = "";
        }
    }
</script>
</body>
</html>