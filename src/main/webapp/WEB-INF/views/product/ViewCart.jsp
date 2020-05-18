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
<script>
var tid;
var cnt = parseInt(180);//초기값(초단위)
function counter_init() {
	tid = setInterval("counter_run()", 1000); //1초
}

function counter_reset() {
	clearInterval(tid);
	cnt = parseInt(180);
	counter_init();
}

function counter_run() {
	document.all.counter.innerText = time_format(cnt);
	cnt--;
	if(cnt < 0) {
		clearInterval(tid);
		alert('장시간 움직이지 않아 로그아웃 합니다.');
		sessionStorage.clear();
		self.location = "/signOut";
	}
}
function time_format(s) {
	var nMin=0;
	var nSec=0;
	if(s>0) {
		nMin = parseInt(s/60);
		nSec = s%60;
	} 
	if(nSec<10) nSec = "0"+nSec;
	if(nMin<10) nMin = "0"+nMin;

	return nMin+":"+nSec;
}
</script>

<script>
var auth = '<%= (String)session.getAttribute("auth") %>';
if(auth != 'null' && auth != 'ROLE_ADMIN'){
counter_init();
}
</script>




</head>
<c:if test = "${signin != null && signin.auth != 'ROLE_ADMIN'}">
<span id="counter"> </span> 초 이후 로그아웃합니다. <input type="button" value="연장" onclick="counter_reset()">
</c:if>
<body>
	<div>
		<table>
			<thead>
				<tr>
					<th>이미지</th>
					<th>상품명</th>
					<th>수량</th>
					<th>표준가격</th>
					<th>할인가격</th>
					
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
									<a href = "/ViewProduct?product_no=${cartList.product_no}">
								<img src ="<spring:url value='/project0518/${cartList.file_name_real}'/>" width = "150" height = "200"/>
									</a>
								</td>
								<td>
								${cartList.product_name}
								</td>
								<td>
                				${cartList.amount}개
								</td>
								<td>
                				<del>${cartList.product_price}원</del>
								</td>
								<td>
								<fmt:formatNumber  value="${cartList.product_price * 0.6}" type="number" var="discount_price" />
                				${discount_price}원(40%)
								</td>
								<td>
								<a href = '#' id = delKey onclick="deleteCart(${cartList.product_no})" >X</a>
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
					<th>표준구매가 -> 총 상품가격</th>
					
				</tr>
					<tr>
					<td>
					<del>${totalPrice}원</del> -> 
					<fmt:formatNumber  value="${totalPrice * 0.6}" type="number" var="total" />
					${total}원
					</td> 
					</tr>
			</tbody>
		</table>
		<a href = "/" >처음으로</a>

	</div>
	</c:if>
	
</body>
</html>