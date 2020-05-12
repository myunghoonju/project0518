<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "spring" uri = "http://www.springframework.org/tags" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상세</title>
</head>
<body>
<div>
 <form id = "viewFrom" >
 
                  <div class="form-group">
                    <label for="image"></label>
                    <img src ="<spring:url value='/project0518/${productDetail.file_name_real}'/>" width = "300" height = "300"/>
                  </div>
                  <div class="form-group">
                    <label for="name">상품명:</label>
                    		${productDetail.product_name}
                  </div>
                  <div class="form-group">
                    <label for="category">상품분류: </label>
                   	 ${productDetail.product_category}
                  </div>
                  <div class="form-group">
                    <label for="price">상품가격:</label>
                    <fmt:formatNumber  value="${productDetail.product_price * 0.6}" type="number" var="discount_price" />
                		표준가격:${productDetail.product_price} -> 임직원 할인가:${discount_price}원<br><br>
                  </div>
</form>

</div>

<div>
<c:choose>
<c:when test = '${empty prevWatchedList || fn:length(prevWatchedList)==1}' >
이전에 본 상품이 없습니다.
</c:when>
<c:otherwise>
<div>최근 본 상품</div><br>
<c:forEach var = "prevWatchedList" items = "${prevWatchedList}">
<img src ="<spring:url value='/project0518/${prevWatchedList.file_name_real}'/>" width = "50" height = "100"/>
${prevWatchedList.product_name}
</c:forEach>
</c:otherwise>
</c:choose>
</div>
</body>
</html>