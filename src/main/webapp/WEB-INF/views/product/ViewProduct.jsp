<%@page import="com.ddc2.project0518.model.UserRegister"%>
<%@page import="com.ddc2.project0518.model.ProductRegister"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "spring" uri = "http://www.springframework.org/tags" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<meta charset="UTF-8">
<title>상세</title>
<script>
$(function(){
	$('#decNum').click(function(e){
		e.preventDefault();
		var value = $('#number').text();
		var num = parseInt(value);
		num--;
		if(num <= 0){
			alert('수량을 선택하세요');
			num = 1;
		}
		$('#number').text(num);
		
	});
	$('#incNum').click(function(e){
		e.preventDefault();
		var value = $('#number').text();
		var num = parseInt(value);
		num++;
		if(num > 100){
			alert('더이상 담을 수 없습니다.');
			num = 100;
		}
		$('#number').text(num);
	});
});
</script>
<script>

function addCart(no){
	var product_no = no;
	var value = $('#number').text();
	alert("상품번호: " + product_no + " 수량:" + value);
	
	$.ajax({
		type : "post",
		url : "/product/addCart",
		data : {
			"product_no":product_no,
			"amount": value		
		},
		beforeSend : function(XMLHttpRequest){
			XMLHttpRequest.setRequestHeader("AJAX", true);
		},
		success:function(data,textStatus){
			if(data.trim() == "exist"){
				alert("이미 담은 상품입니다.");
			}else if(data.trim() == "success"){
				alert("장바구니에 담았습니다.")
			}
		},
		error:function(e){
			if(e.status==500){
				alert("로그인 후 이용가능 합니다.");
				location.replace("/users/signin");
			}
		}
	});
	
};
</script>
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
                  <div class ="num">
                  <span>수량:</span>
                  <strong><a href = "#" id = "incNum">+</a></strong>
                 	<span id = "number">1</span>
                  <strong><a href = "#" id ="decNum">-</a></strong>
                  <button type = "button" class= "cart" onclick="addCart(${productDetail.product_no})">장바구니 담기</button><br><br>
                  </div>
                  
                  
</form>

</div>

<div>
<a href = "/goCart">장바구니 보러가기</a>
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