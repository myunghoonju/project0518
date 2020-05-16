<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri = "http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>시작페이지</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<style>

#modal{
  display:none;
  position:fixed; 
  width:100%; height:100%;
  top:0; left:0; 
  background:rgba(0,0,0,0.3);
}
.modal-con{
  display:none;
  position:fixed;
  top:50%; left:50%;
  transform: translate(-50%,-50%);
  max-width: 60%;
  min-height: 30%;
  background:#fff;
}
.modal-con .title{
  font-size:15px; 
  padding: 10px; 
  background : gold;
}
.modal-con .con{
  font-size:15px; line-height:1.3;
  padding: 30px;
}
.modal-con .close{
  display:block;
  position:absolute;
  width:30px; height:30px;
  border-radius:50%; 
  border: 2px solid #000;
  text-align:center;
  line-height: 30px;
  text-decoration:none;
  color:#000;
  font-size:20px;
  font-weight: bold;
  right:10px; top:10px;
}
.label{
	font-size: 13px;
	margin:10px;
	padding:0 10px;
	color : #999;
}
.form input{
	width:100%;
	height:20px;
	padding: 0 15px;
	box-sizing: border-box;
	border-radius: 5px;
	border:1px solid #ddd;
}

a.button{
	display:inline-block;
	padding: 10px 20px;
	text-decoration:none;
	color:#fff;
	background:#000;
	margin:20px;
}
</style>

<script>

function openModal(modalName){
	  document.get
	  $("#modal").fadeIn(300);
	  $("."+modalName).fadeIn(300);
	}

</script>
<script type="text/javascript">
$(function(){
	$('#insert').click(function(){
		var userid = $('#userid').val();
		var password = $('#password').val();
		var name = $('#name').val();
		var email = $('#email').val();
		
		var param =  {
			userid : userid,
			password : password,
			name : name,
			email : email	
		};
		
		$.ajax({
			type : "post",
			url : "/userJoin",
			dataType : 'json',
			contentType : 'application/json; charset=utf-8',
			data : JSON.stringify(param),
			
			success:function(data,textStatus){
				alert("성공");
				console.log(data);
			},
			error:function(data,textStatus){
				alert("실패");
			}
		});
	})
	$("#modal").fadeOut(300);
	$(".modal-con").fadeOut(300);	
})

</script>
<script>
function manageAll(){
	 var auth = "${signin.auth}";
	 if(auth == 'ROLE_USER'){
		 alert('관리자만 볼 수 있는 메뉴입니다.');
		 return false;
	 }
	 if(auth == 'ROLE_ADMIN'){
		 location.replace('/admin/ManageAll');
	 }
}
</script>
</head>
<body>

	<c:if test = "${signin == null}">
	
	<c:forEach var = "ProductList" items = "${ProductList}">
	<a href = "/ViewProduct?product_no=${ProductList.product_no}">
	<img src ="<spring:url value='/project0518/${ProductList.file_name_real}'/>" width = "150" height = "200" class="prodImage"/>
	</a>
	${ProductList.product_category}

	</c:forEach>
	<a href="javascript:openModal('joinModal');" class="button modal-open">회원가입</a>
	<a href ="/users/signin" class="button modal-open">로그인</a>
	
	</c:if>
	
	<c:if test = "${signin != null}">
	<a href = "/signOut" class="button modal-open">로그아웃</a>
	<a href = "/admin/AddProduct" class="button modal-open">상품등록</a>
	<a href = "javascript:manageAll();" class="button modal-open">정보관리</a>
	
	<c:forEach var = "ProductList" items = "${ProductList}">
	<a href = "/ViewProduct?product_no=${ProductList.product_no}">
	<img src ="<spring:url value='/project0518/${ProductList.file_name_real}'/>" width = "150" height = "200"/>
	</a>
	${ProductList.product_category}

	</c:forEach>
	</c:if>
	
	
	
	
	
	
	
	
	

<div id="modal">
  <div class="modal-con joinModal">
    <a href="" id ="insert" class="close">X</a>
    <p class = "title">회원가입</p>
    <div class="con">
    	<form id = "joinForm" action = "" method="post">
     		 <div class="label">아이디</div>
				<div class = "form">
				<input type="text" class="form-control" id="userid" name="userid">
				<small id="userid" class="text-info"></small>
              	</div>
  			 <div class="label">비밀번호</div>
  			 	<div class = "form">
				<input type="password" id="password" name="password" class="form-control">
				<small id="password" class="text-info"></small>
				</div>
			<div class="label">이름</div>
				<div class = "form">
				<input type="text" id="name" name=name class="form-control">
				<small id="name" class="text-info"></small>
				</div>
			 <div class="label">이메일</div>
			 	<div class = "form">
				<input type="email" id="email" name="email" class="form-control">
				<small id="email" class="text-info"></small>
				</div>
		</form>	
	</div>
  </div>
</div>
</body>
</html>