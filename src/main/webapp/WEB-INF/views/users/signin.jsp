<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
</head>
<body>
	<div>
		<form id="signin-form" name="signin-form" action = "/signinProcess" method="post">
			<label >아이디</label>
			<input id="userid" name="userid" type="text" value="">
			<label >패스워드</label>
			<input id="password" name="password" type="password" value="">
			<input type="submit" value="로그인">
		</form>
	</div>
</body>
</html>