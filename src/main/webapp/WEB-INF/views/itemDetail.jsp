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
        .half-active::before {
            content: '\f089'; /* 반 별 모양 */
            font-family: "Font Awesome 5 Free";
            font-weight: 900;
            color: gold;
            position: absolute;
            top: 0;
            left: 0;
            width: 100%; /* 별의 전체를 보이게 설정 */
            overflow: hidden; /* 별의 오른쪽 반을 숨김 */
            z-index: 1; /*별의 레이어를 상위로 설정*/
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
        <strong>평점</strong><div class="rating-display" data-rating="${item.averageRating}">
            <span class="fa fa-star"></span>
            <span class="fa fa-star"></span>
            <span class="fa fa-star"></span>
            <span class="fa fa-star"></span>
            <span class="fa fa-star"></span>
        </div>
        <img src="${item.productImage}" alt="${item.name}" width="250">
    </div>

    <!-- 평점 입력 섹션 -->
    <form action="/rate/rating/${item.itemId}" method="post">
        <label for="tempUserId">User ID:</label>
        <input type="text" id="tempUserId" name="tempUserId" required><br>

        <strong>평점</strong>
        <div class="rating-input">
            <label><input type="radio" name="ratingValue" value="5" id="star5"> 최고 <span class="fa fa-star" style="color: gold;"></span><span class="fa fa-star" style="color: gold;"></span><span class="fa fa-star" style="color: gold;"></span><span class="fa fa-star" style="color: gold;"></span><span class="fa fa-star" style="color: gold;"></span></label><br>
            <label><input type="radio" name="ratingValue" value="4" id="star4"> 좋음 <span class="fa fa-star" style="color: gold;"></span><span class="fa fa-star" style="color: gold;"></span><span class="fa fa-star" style="color: gold;"></span><span class="fa fa-star" style="color: gold;"></span></label><br>
            <label><input type="radio" name="ratingValue" value="3" id="star3"> 보통 <span class="fa fa-star" style="color: gold;"></span><span class="fa fa-star" style="color: gold;"></span><span class="fa fa-star" style="color: gold;"></span></label><br>
            <label><input type="radio" name="ratingValue" value="2" id="star2"> 별로 <span class="fa fa-star" style="color: gold;"></span><span class="fa fa-star" style="color: gold;"></span></label><br>
            <label><input type="radio" name="ratingValue" value="1" id="star1"> 나쁨 <span class="fa fa-star" style="color: gold;"></span></label><br>
        </div>
    <textarea id="ratingText" name="ratingText" placeholder="평점 내용을 작성해주세요."></textarea>
    <button id="submitRating">평점 제출</button>
    </form>

    <!--평점 목록-->
    <div class="rating-list">
    <c:forEach items="${ratings}" var="rating">
        <div class="rating-item">
            <div>
                <c:forEach begin="1" end="${rating.ratingValue}" var="star">
                    <span class="fa fa-star" style="color: gold;"></span>
                </c:forEach>
                <c:forEach begin="${rating.ratingValue + 1}" end="5" var="emptyStar">
                    <span class="fa fa-star" style="color: gray;"></span>
                </c:forEach>
            </div>
            <p>${rating.ratingText}</p>
            <button onclick="handleEdit(${rating.ratingId})" class="btn btn-edit">수정</button>
            <button onclick="handleDelete(${rating.ratingId})" class="btn btn-delete">삭제</button>
        </div>
    </c:forEach>
    </div>

    <!-- 상품 상세 정보 섹션 -->
    <div class="item-detail">
        <h2>Product Details</h2>
        <!-- 임의 내용 -->
        <p>상품 상세 설명임</p>
        <p>상품 상세 설명임</p>
        <p>상품 상세 설명임</p>
        <p>상품 상세 설명임</p>
        <p>상품 상세 설명임</p>
        <p>상품 상세 설명임</p>
        <p>상품 상세 설명임</p>
        <p>상품 상세 설명임</p>
        <p>상품 상세 설명임</p>
        <p>상품 상세 설명임</p>
        <p>상품 상세 설명임</p>
        <p>상품 상세 설명임</p>
        <p>상품 상세 설명임</p>
        <p>상품 상세 설명임</p>
        <p>상품 상세 설명임</p>
        <p>상품 상세 설명임</p>
        <p>상품 상세 설명임</p>
        <p>상품 상세 설명임</p>
        <p>상품 상세 설명임</p>
        <p>상품 상세 설명임</p>
        <p>상품 상세 설명임</p>
        <p>상품 상세 설명임</p>
        <p>상품 상세 설명임</p>
        <p>상품 상세 설명임</p>
        <p>상품 상세 설명임</p>
        <p>상품 상세 설명임</p>
        <p>상품 상세 설명임</p>
        <p>상품 상세 설명임</p>
        <p>상품 상세 설명임</p>
        <p>상품 상세 설명임</p>
        <p>상품 상세 설명임</p>
        <p>상품 상세 설명임</p>
        <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed sit amet facilisis urna. Praesent ac gravida libero. Donec non tortor in arcu mollis tincidunt. Suspendisse maximus enim ac eros varius, in posuere risus facilisis. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer at volutpat libero. Vivamus efficitur, nibh a fringilla condimentum, nisl quam commodo magna, at facilisis orci nisi eget ex. Donec quis facilisis orci. Curabitur pretium, neque vitae pretium volutpat, orci sem luctus erat, in cursus libero justo ac nisi. Sed erat magna, pharetra at orci at, volutpat lacinia lorem. Sed ac est a sem aliquet rhoncus. Maecenas fringilla, purus in placerat iaculis, velit sem convallis eros, sed porttitor justo tellus at est. Etiam lacinia feugiat massa a feugiat. Curabitur a luctus orci.</p>
    </div>

    <script>
        document.addEventListener('DOMContentLoaded', function() {
            // 상품의 평점 표시
            const ratingDisplay = document.querySelector('.rating-display');
            let displayRateValue = parseFloat(ratingDisplay.getAttribute('data-rating'));
            displayRateValue = Math.round(displayRateValue * 10) / 10;
            const displayFullStars = Math.floor(displayRateValue);
            const displayPartialFill = displayRateValue - displayFullStars;
            for (let i = 0; i < displayFullStars; i++) {
                ratingDisplay.children[i].classList.add('active-star');
            }
            if (displayPartialFill >= 0.5) {
                const halfStar = ratingDisplay.children[displayFullStars];
                halfStar.classList.add('half-star');
            }

            // 사용자 평점 입력
            const ratingInputStars = document.querySelectorAll('.rating-input .fa-star-rating');
            ratingInputStars.forEach(star => {
                star.addEventListener('click', function(e) {
                    const value = parseFloat(star.getAttribute('data-value'));
                    const rect = e.target.getBoundingClientRect();
                    const offsetX = e.clientX - rect.left;
                    let finalValue = value;
                    if (offsetX < rect.width / 2) {
                        finalValue -= 0.5;
                    }
                    document.getElementById('ratingValue').value = finalValue;
                    for (let i = 0; i < ratingInputStars.length; i++) {
                        if (i < finalValue) {
                            ratingInputStars[i].classList.add('active-star');
                            ratingInputStars[i].classList.remove('half-active');
                        } else if (i + 0.5 == finalValue) {
                            ratingInputStars[i].classList.remove('active-star');
                            ratingInputStars[i].classList.add('half-active');
                        } else {
                            ratingInputStars[i].classList.remove('active-star', 'half-active');
                        }
                    }
                });
            });
        });
    </script>
    <script>
        function getCookie(name) {
            let value = "; " + document.cookie;
            let parts = value.split("; " + name + "=");
            if (parts.length == 2) return parts.pop().split(";").shift();
        }
        function handleEdit(ratingId) {
            let userId = prompt("user ID 입력 :");
            let sessionUserId = getCookie("tempUserId");
            if (userId === sessionUserId) {
                fetch(`/rate/${ratingId}`)
                    .then(response => response.json())
                    .then(data => {
                        // 평점 입력 칸에 데이터 설정
                        document.getElementById("ratingText").value = data.ratingText;
                        // 별점 설정 (예: 별점이 5개라면)
                        for (let i = 1; i <= 5; i++) {
                            if (i <= data.ratingValue) {
                                document.getElementById(`star${i}`).checked = true;
                            } else {
                                document.getElementById(`star${i}`).checked = false;
                            }
                        }
                    });
            } else {
                alert("user ID가 맞지 않습니다.");
            }
        }
        function handleDelete(ratingId) {
            let userId = prompt("user ID 입력 :");
            let sessionUserId = getCookie("tempUserId");
            if (userId === sessionUserId) {
                fetch(`/rate/delete/${ratingId}`, {
                    method: 'DELETE'
                })
                    .then(response => {
                        if (response.ok) {
                            alert("평점이 삭제되었습니다.");
                            location.reload(); // 페이지를 새로고침하여 삭제된 평점을 반영
                        } else {
                            alert("평점 삭제에 실패했습니다.");
                        }
                    });
            } else {
                alert("user ID가 맞지 않습니다.");
            }
        }
        console.log("Session User ID (from cookie):", getCookie("tempUserId"));
    </script>
</body>
</html>
