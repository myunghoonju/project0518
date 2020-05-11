<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>시작페이지</title>
</head>
<body>
	<c:if test = "${signin == null}">
	<a href ="/users/signin">로그인</a>
	<a href = "/product/AddProduct">등록테스트</a>
	</c:if>
	<c:if test = "${signin.name != null}">
	${signin.name}님 환영합니다.
	<a href = "/signOut">로그아웃</a>
	<a href = "/product/AddProduct">등록테스트</a>
	</c:if>
	
	
	
	
</body>
</html>