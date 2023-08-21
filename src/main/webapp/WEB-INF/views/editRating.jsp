<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Rating</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css">
    <style>
        .active-star {
            color: gold;
        }
        .rating-input .fa-star {
            color: #ccc;
            cursor: pointer;
        }
    </style>
</head>
<body>
<h1>Edit your rating</h1>
<form action="<%= request.getContextPath() %>/rate/edit/${rating.ratingId}" method="post">

    <label for="ratingValue">Rating Value:</label>
    <div class="rating-input">
        <c:forEach var="i" begin="1" end="5">
            <span class="fa fa-star inactive-star" data-value="${i}" onclick="handleStarClick(${i})"></span>
        </c:forEach>
        <input type="number" id="ratingValue" name="ratingValue" min="1" max="5" required style="display: none">
    </div>
    <br><br>

    <div>
        <!-- 이미지 표시 및 삭제 로직 -->
        <c:forEach var="image" items="${ratingImages}" varStatus="loop">
            <div>
                <img src="${image}" alt="Uploaded Image" width="150" />
                <img id="imagePreview${loop.index}" width="150" alt="Preview Image" />
                <button onclick="event.preventDefault(); deleteImage('${image}');">-</button>
            </div>
        </c:forEach>

        <!-- 이미지 추가 로직 -->
        <div>
            <input type="file" name="ratingImages" multiple onchange="previewImage(event)">
            <div>
                <c:forEach var="i" begin="1" end="4">
                    <img id="imagePreview${i}" width="150" alt="Preview Image">
                </c:forEach>
            </div>
        </div>
    </div>
    <br><br>

    <div>
        <label for="ratingText">Rating Text:</label>
        <textarea id="ratingText" name="ratingText" rows="4" cols="50" required>${rating.ratingText}</textarea>
    </div>
    <button type="submit">Update Rating</button>
</form>
<br>
<a href="<%= request.getContextPath() %>/items/${rating.itemId}">Back to itemDetail</a>

<script>
    let selectedImages = [];

    document.addEventListener('DOMContentLoaded', function() {
        const ratingValue = parseInt('${rating.ratingValue}');
        setStars(ratingValue);
    });

    function handleStarClick(starValue) {
        setStars(starValue);
        const ratingInputElement = document.getElementById('ratingValue');
        ratingInputElement.value = starValue;
        if (!ratingInputElement.value) {
            alert("Please rate the item before submitting.");
            return false;
        }
    }

    function setStars(starValue) {
        resetStars();
        const ratingStars = document.querySelectorAll('.rating-input .fa-star');
        for (let i = 0; i < starValue; i++) {
            if (ratingStars[i]) {
                ratingStars[i].classList.add('active-star');
                ratingStars[i].classList.remove('inactive-star');
            }
        }
    }

    function resetStars() {
        const ratingStars = document.querySelectorAll('.rating-input .fa-star');
        ratingStars.forEach(star => {
            star.classList.remove('active-star');
            star.classList.add('inactive-star');
        });
    }

    function previewImage(event) {
        const files = event.target.files;
        for(let i = 0; i < files.length && i < 4; i++) {
            selectedImages.push(files[i]);
            const reader = new FileReader();
            reader.onload = function(e) {
                document.getElementById('imagePreview' + (i + 1)).src = e.target.result;
            };
            reader.readAsDataURL(files[i]);
        }
    }

    function deleteImage(imagePath) {
        const formData = new FormData();
        formData.append('imagePath', imagePath);
        fetch('<%= request.getContextPath() %>/edit/${rating.ratingId}/deleteImage', {
            method: 'POST',
            body: formData
        }).then(response => {
            if (response.ok) {
                location.reload();
            } else {
                alert('Error deleting image. Please try again later.');
            }
        });
    }
</script>
</body>
</html>
