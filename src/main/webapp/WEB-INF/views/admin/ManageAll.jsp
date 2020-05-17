<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt"  uri = "http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>관리자 페이지</title>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script>
function deleteUser(userid){
	var userid = userid;
	alert("사용자[" + userid+"]");
	
	$.ajax({
		type : "post",
		url : "/admin/deleteUser",
		data : {
			"userid":userid,		
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
function deleteProduct(no){
	var product_no = no;
	alert("상품번호[" + product_no+"]");
	
	$.ajax({
		type : "post",
		url : "/admin/deleteProduct",
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
			<caption>사용자정보관리</caption>
			<thead>
				<tr>
					<th>사용자아이디</th>
					<th>사용자이름</th>
					<th>사용자이메일</th>
					<th>사용자등급</th>
					<th>사용자가입일</th>
					<th>정보수정일자</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${userInfo.size() ==0}">
						<tr>
							<td>아무도 없습니다.</td>
						</tr>
					</c:when>
					<c:otherwise>
						<c:forEach var = "userInfo" items = "${userInfo}">
							<tr>
								<td>
							<a href ="/admin/updateUser?userid=${userInfo.userid}" >${userInfo.userid}</a>
								</td>
								<td>
								${userInfo.name}
								</td>
								<td>
								${userInfo.email}
								</td>
								<td>
								${userInfo.auth}
								</td>
								<td>
								${userInfo.register_date}
								</td>
								<td>
								${userInfo.update_date}
								</td>
								<td>
								<a href = '#' id = delUserKey onclick="deleteUser('${userInfo.userid}')" >X</a>
								</td>
							</tr>
						</c:forEach> 
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>
	</div>
	
	<div>
		<table>
			<caption>등록상품정보관리</caption>
			<thead>
				<tr>
					<th>상품번호</th>
					<th>상품명</th>
					<th>상품가격</th>
					<th>상품구분</th>
					<th>등록일자</th>
					<th>정보수정일자</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${prodInfo.size() ==0}">
						<tr>
							<td>등록된 상품이 존재하지 않습니다.</td>
						</tr>
					</c:when>
					<c:otherwise>
						<c:forEach var = "prodInfo" items = "${prodInfo}">
							<tr>
								<td>
								<fmt:formatNumber  value="${prodInfo.product_no}" type="number" var="product_no" />
							<a href ="/admin/updateProduct?product_no=${product_no}" >${product_no}</a>
								
								</td>
								<td>
								${prodInfo.product_name}
								</td>
								<td>
								<fmt:formatNumber  value="${prodInfo.product_price}" type="number" var="product_price" />
								${product_price}
								</td>
								<td>
								${prodInfo.product_category}
								</td>
								<td>
								${prodInfo.register_date}
								</td>
								<td>
								${prodInfo.update_date}
								</td>
								<td>
								<a href = '#' id = delProductKey onclick="deleteProduct(${prodInfo.product_no})" >X</a>
								</td>
							</tr>
						</c:forEach> 
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>
	</div>
</body>
</html>