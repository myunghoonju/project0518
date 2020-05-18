<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원정보 수정[관리자]</title>
</head>
<body>
	<div>
	<form action = "/admin/updateProduct" method = "POST">
		<table>
			<thead>
				<tr>
					<th>상품번호</th>
					<th>상품명</th>
					<th>상품가격</th>
					<th>상품분류</th>
					<th>상품등록일</th>
					<th>정보수정일</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>
					<span>상품번호는 수정할 수 없습니다.</span><br>
					<input type = "number" name ="product_no" value = "${prodInfo.product_no}" readonly="readonly">${prodInfo.product_no}
					</td>
					<td><input type="text" name="product_name" placeholder="${prodInfo.product_name}" ></td>
					<td><input type="text" name="product_price" placeholder="${prodInfo.product_price}"></td>
					<td><input type="text" name="product_category" placeholder="${prodInfo.product_category}"></td>
					<td>${prodInfo.register_date}</td>
					<td>${prodInfo.update_date}</td>
				</tr>
			</tbody>
		</table>
		<button type = "submit">수정</button>
		<a href ="/admin/ManageAll">돌아가기</a>
	</form>
	</div>


</body>
</html>