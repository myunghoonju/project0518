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
	<form action = "/admin/updateUser" method = "POST">
		<table>
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
				<tr>
					<td>
					<span>아이디는 수정할 수 없습니다.</span><br>
					<input type = "text" name ="userid" value = "${userInfo.userid}" readonly="readonly">${userInfo.userid}
					</td>
					<td><input type="text" name="name" placeholder="${userInfo.name}"></td>
					<td><input type="text" name="email" placeholder="${userInfo.email}"></td>
					<td><input type="text" name="auth" placeholder="${userInfo.auth}"></td>
					<td>${userInfo.register_date}</td>
					<td>${userInfo.update_date}</td>
				</tr>
			</tbody>
		</table>
		<button type = "submit">수정</button>
	</form>
	</div>


</body>
</html>