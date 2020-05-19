<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri = "http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="zxx">

<head>

    <meta charset="UTF-8">
    <meta name="description" content="Ogani Template">
    <meta name="keywords" content="Ogani, unica, creative, html">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>작성자:주명훈</title>

    <!-- Google Font -->
    <link href="https://fonts.googleapis.com/css2?family=Cairo:wght@200;300;400;600;900&display=swap" rel="stylesheet">

    <!-- Css Styles -->
    <link rel="stylesheet" href="../resources/css/bootstrap.min.css" type="text/css">
    <link rel="stylesheet" href="../resources/css/font-awesome.min.css" type="text/css">
    <link rel="stylesheet" href="../resources/css/elegant-icons.css" type="text/css">
    <link rel="stylesheet" href="../resources/css/nice-select.css" type="text/css">
    <link rel="stylesheet" href="../resources/css/jquery-ui.min.css" type="text/css">
    <link rel="stylesheet" href="../resources/css/owl.carousel.min.css" type="text/css">
    <link rel="stylesheet" href="../resources/css/slicknav.min.css" type="text/css">
    <link rel="stylesheet" href="../resources/css/style.css" type="text/css">
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

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>  
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
			contentType : 'application/json; charset=utf-8',
			data : JSON.stringify(param),
			
			success:function(data,textStatus){
				if(data.trim() == 'success')
					alert("성공");
			},
			error:function(data,textStatus){
				if(data.trim() == 'failed')
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

<body>
    <!-- Page Preloder -->
    <div id="preloder">
        <div class="loader"></div>
    </div>

    <!-- Humberger Begin -->
    <div class="humberger__menu__overlay"></div>
    <div class="humberger__menu__wrapper">
        <div class="humberger__menu__logo">
            <a href="#"><img src="../resources/img/logo.png" alt=""></a>
        </div>
        <div class="humberger__menu__cart">
            <ul>
                <li><a href="#"><i class="fa fa-heart"></i> <span>1</span></a></li>
                <li><a href="#"><i class="fa fa-shopping-bag"></i> <span>3</span></a></li>
            </ul>
            <div class="header__cart__price">item: <span>$150.00</span></div>
        </div>
        <div class="humberger__menu__widget">
            <div class="header__top__right__language">
                <img src="../resources/img/language.png" alt="">
                <div>English</div>
                <span class="arrow_carrot-down"></span>
                <ul>
                    <li><a href="#">Spanis</a></li>
                    <li><a href="#">English</a></li>
                </ul>
            </div>
            <div class="header__top__right__auth">
                <a href="#"><i class="fa fa-user"></i> Login</a>
            </div>
        </div>
        <nav class="humberger__menu__nav mobile-menu">
            <ul>
                <li class="active"><a href="./index.html">Home</a></li>
                <li><a href="./shop-grid.html">Shop</a></li>
                <li><a href="#">Pages</a>
                    <ul class="header__menu__dropdown">
                        <li><a href="./shop-details.html">Shop Details</a></li>
                        <li><a href="./shoping-cart.html">Shoping Cart</a></li>
                        <li><a href="./checkout.html">Check Out</a></li>
                        <li><a href="./blog-details.html">Blog Details</a></li>
                    </ul>
                </li>
                <li><a href="./blog.html">Blog</a></li>
                <li><a href="./contact.html">Contact</a></li>
            </ul>
        </nav>
        <div id="mobile-menu-wrap"></div>
        <div class="header__top__right__social">
            <a href="#"><i class="fa fa-facebook"></i></a>
            <a href="#"><i class="fa fa-twitter"></i></a>
            <a href="#"><i class="fa fa-linkedin"></i></a>
            <a href="#"><i class="fa fa-pinterest-p"></i></a>
        </div>
        <div class="humberger__menu__contact">
            <ul>
                
            </ul>
        </div>
    </div>
    <!-- Humberger End -->

    <!-- Header Section Begin -->
    <header class="header">
        <div class="header__top">
            <div class="container">
                <div class="row">
                    <div class="col-lg-6">
                        <div class="header__top__left">
                            <ul>
                               <li><c:if test = "${signin != null && signin.auth != 'ROLE_ADMIN'}">
				<span id="counter"> </span> 초 이후 로그아웃합니다. <input type="button" value="연장" onclick="counter_reset()">
				</c:if></li>
             
                            </ul>
                        </div>
                    </div>
                    <div class="col-lg-6">
                        <div class="header__top__right">
                            <div class="header__top__right__social">
                                
                            </div>
                            <div class="header__top__right__language">
                              
                            </div>
                            <c:if test = "${signin == null}">
                             <div class="header__top__right__auth">
							<a href ="/users/signin" ><i class="fa fa-user"></i>로그인</a>
							</div>
                            <div class="header__top__right__auth">
                   <a href="javascript:openModal('joinModal');" ><i class="fa fa-user"></i>회원가입</a>
                            </div>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="container">
            <div class="row">
                <div class="col-lg-3">
                    <div class="header__logo">
                        <a href="/"><img src="../resources/img/logo.png" alt=""></a>
                    </div>
                </div>
                <div class="col-lg-6">
                    <nav class="header__menu">
                        <ul>
                           
                           <c:if test = "${signin != null}">
                           <li><a href = "/signOut">로그아웃</a></li>
                            <li><a href="#">관리자목록</a>
                                <ul class="header__menu__dropdown">
                                	<li><a href = "/admin/AddProduct">상품등록</a></li>
									<li><a href = "javascript:manageAll();" >정보관리</a></li>
                                </ul>
                            </li>
                            </c:if>
                        </ul>
                    </nav>
                </div>
                <div class="col-lg-3">
                    <div class="header__cart">
                      
                    </div>
                </div>
            </div>
            <div class="humberger__open">
                
            </div>
        </div>
    </header>
    <!-- Header Section End -->

    <!-- Hero Section Begin -->
    <section class="hero hero-normal">
        <div class="container">
            <div class="row">
                <div class="col-lg-3">
                    <div class="hero__categories">
                        
                        
                    </div>
                </div>
                <div class="col-lg-9">
                    <div class="hero__search">
                        <div class="hero__search__form">
                           
                        </div>
                        <div class="hero__search__phone">
                           
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <!-- Hero Section End -->

    <!-- Breadcrumb Section Begin -->
    <section class="breadcrumb-section set-bg" data-setbg="../resources/img/breadcrumb.jpg">
        <div class="container">
            <div class="row">
                <div class="col-lg-12 text-center">
                    <div class="breadcrumb__text">
                        <h2>메인메뉴</h2>
                        <div class="breadcrumb__option">
                           <i class="fa fa-heart" aria-hidden="true"></i>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <!-- Breadcrumb Section End -->

    <!-- Product Section Begin -->
    <section class="product spad">
        <div class="container">
            <div class="row">
                <div class="col-lg-3 col-md-5">
                    <div class="sidebar">
                        
                     </div>
                        <div class="sidebar__item sidebar__item__color--option">
                            
                        </div>
                        <div class="sidebar__item">
                           
                        </div>
                        <div class="sidebar__item">
                            <div class="latest-product__text">                       
                            </div>           
                         </div>      
                 </div>
                
                <div class="col-lg-9 col-md-7">
                    <div class="product__discount">
                        <div class="section-title product__discount__title">
                           
                        </div>
                        <div class="row">
                            
                        </div>
                    </div>
                    <div class="filter__item">
                        <div class="row">
                            <div class="col-lg-4 col-md-5">
                                <div class="filter__sort">
                                   
                                </div>
                            </div>
                            <div class="col-lg-4 col-md-4">
                                <div class="filter__found">
                                   
                                </div>
                            </div>
                            <div class="col-lg-4 col-md-3">
                                <div class="filter__option">
                                 
                                </div>
                            </div>
                        </div>
                    </div>
                  
                    <div class="row" align="center">
                    <c:forEach var = "ProductList" items = "${ProductList}" >
                        <div class="col-lg-4 col-md-6 col-sm-6">
                            <div class="product__item">
                            
                                <div class="product__item__pic set-bg">
                                	
                      
                                        <a href = "/ViewProduct?product_no=${ProductList.product_no}">
										<img src ="<spring:url value='/project0518/${ProductList.file_name_real}'/>" width = "150" height = "200" class="prodImage"/>
										</a>
                                   
                                </div>
                                <div class="product__item__text">
                                   <h6>${ProductList.product_category}</h6>
                                   <span>${ProductList.product_price}</span>
                                </div>
                                
                            </div>
                        </div>
                    </c:forEach>
                    </div>
            <div class="product__pagination" align="center">
            <c:choose>
 				<c:when test = "${page.prev eq false}">
 				<i class="fa fa-long-arrow-left"></i>
 				</c:when>
 				<c:otherwise>
				
				<a href = "/index/${page.make_query(page.start_page -1)}"><i class="fa fa-long-arrow-left"></i></a>
				
				</c:otherwise>
			</c:choose>
			<c:if test = "${page.next && page.end_page >0}">
				
				<a href = "/index/${page.make_query(page.start_page +1)}"><i class="fa fa-long-arrow-right"></i></a>
				
			</c:if>   
            </div>
          </div>
        </div>
     </div>
     
     
     
<div id="modal">
  <div class="modal-con joinModal">
    <a href="" id ="insert" class="close">X</a>
    <p class = "title">회원가입</p>
    <div class="con">
    	<form id = "joinForm" action = "" method="post">
     		 <div class="label">아이디</div>
				<div class = "form">
				<input type="text" class="form-control" id="userid" name="userid" required="required">
				<small id="userid" class="text-info"></small>
              	</div>
  			 <div class="label">비밀번호</div>
  			 	<div class = "form">
				<input type="password" id="password" name="password" class="form-control" required="required">
				<small id="password" class="text-info"></small>
				</div>
			<div class="label">이름</div>
				<div class = "form">
				<input type="text" id="name" name=name class="form-control" required="required">
				<small id="name" class="text-info"></small>
				</div>
			 <div class="label">이메일</div>
			 	<div class = "form">
				<input type="email" id="email" name="email" class="form-control" placeholder="@kr.doubledown.com">
				<small id="email" class="text-info"></small>
				</div>
		</form>	
	</div>
  </div>
</div>
     
     
     
     
     
     

    </section>
    <!-- Product Section End -->























    <!-- Footer Section Begin -->
    <footer class="footer spad">
        <div class="container">
            <div class="row">
                <div class="col-lg-3 col-md-6 col-sm-6">
                    <div class="footer__about">
                        <div class="footer__about__logo">
                           
                        </div>
                        <ul>
                        </ul>
                    </div>
                </div>
                <div class="col-lg-4 col-md-6 col-sm-6 offset-lg-1">
                    <div class="footer__widget">
                      
                    </div>
                </div>
                <div class="col-lg-4 col-md-12">
                    <div class="footer__widget">
                       
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-12">
                    <div class="footer__copyright">
                        <div class="footer__copyright__text"><p><!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->
  Copyright &copy;<script>document.write(new Date().getFullYear());</script> All rights reserved | 프로젝트 작성자 <i class="fa fa-heart" aria-hidden="true"></i><a href="https://github.com/myunghoonju/project0518" target="_blank">주명훈</a>
  <!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. --></p></div>
                        <div class="footer__copyright__payment"><img src="../resources/img/payment-item.png" alt=""></div>
                    </div>
                </div>
            </div>
        </div>
    </footer>
    <!-- Footer Section End -->

    <!-- Js Plugins -->
    <script src="../resources/js/jquery-3.3.1.min.js"></script>
    <script src="../resources/js/bootstrap.min.js"></script>
    <script src="../resources/js/jquery.nice-select.min.js"></script>
    <script src="../resources/js/jquery-ui.min.js"></script>
    <script src="../resources/js/jquery.slicknav.js"></script>
    <script src="../resources/js/mixitup.min.js"></script>
    <script src="../resources/js/owl.carousel.min.js"></script>
    <script src="../resources/js/main.js"></script>



</body>

</html></html>