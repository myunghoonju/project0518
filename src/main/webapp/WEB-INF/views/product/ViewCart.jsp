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
<title>장바구니</title>
<script>
function deleteCart(no){
	var product_no = no;
	alert("상품번호[" + product_no+"]");
	
	$.ajax({
		type : "post",
		url : "/product/deleteCart",
		data : {
			"product_no":product_no,		
		},
		success:function(data,textStatus){
			if(data.trim() == "success"){
				alert("삭제되었습니다.");
				location.reload();
			}
		},
		error:function(data,textStatus){
			alert("삭제하지 못했습니다.");
		}
	});
	
};







</script>
</head>
<body>
	<div>
		<table>
			<thead>
				<tr>
					<th>이미지</th>
					<th>상품명</th>
					<th>가격</th>
					<th>수량</th>
					<th>합계</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${cartList.size() ==0}">
						<tr>
							<td>상품을 바구니에 담아주세요!</td>
						</tr>
					</c:when>
					<c:otherwise>
						<c:forEach var = "cartList" items = "${cartList}">
							<tr>
								<td>
								<img src ="<spring:url value='/project0518/${cartList.file_name_real}'/>" width = "150" height = "200"/>
								</td>
								<td>
								${cartList.product_name}
								</td>
								<td>
								<fmt:formatNumber  value="${cartList.product_price * 0.6}" type="currency" var="discount_price" />
                				${discount_price}원
								</td>
								<td>
                				${cartList.amount}개
								</td>
								<td>
								<fmt:formatNumber  value="${cartList.product_price * cartList.amount}" type="number" var="total_price" />
								${total_price}원
								</td>
								<td>
								<a href = '#' id = delKey onclick="deleteCart(${cartList.product_no})" >X</span>
								</td>
							</tr>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>
	</div>
	
	
	<c:if test ="${cartList.size() != 0}">
	<div>
		<h5>장바구니 합계 영역</h5>
		<table>
			<tbody>
				<tr>
					<th>가격테스트</th>
					<td>
						--
					</td>
				</tr>
				<tr>
					<th>총구매 합계</th>
					<td>
					--합계영역
					</td>
				</tr>
			</tbody>
		</table>
		<a href = "#" >단추1</a>
		<a href = "#" >단추2</a>
		<a href = "#" >단추3</a>
	</div>
	</c:if>
	
</body>
</html>