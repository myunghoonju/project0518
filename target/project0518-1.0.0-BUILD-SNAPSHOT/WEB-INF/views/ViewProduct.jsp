<%@page import="com.ddc2.project0518.model.UserRegister"%>
<%@page import="com.ddc2.project0518.model.ProductRegister"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "spring" uri = "http://www.springframework.org/tags" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="zxx">

<head>
    <meta charset="UTF-8">
    <meta name="description" content="Ogani Template">
    <meta name="keywords" content="Ogani, unica, creative, html">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">

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
    
<script src="http://code.jquery.com/jquery-latest.js"></script>    

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
		url : "/addCart",
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
    <div class="humberger__menu__overlay">
    <c:if test = "${signin != null && signin.auth != 'ROLE_ADMIN'}">
		<span id="counter"> </span> 초 이후 로그아웃합니다. <input type="button" value="연장" onclick="counter_reset()">
	</c:if>
    </div>
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
                <div></div>
                <span class="arrow_carrot-down"></span>
                <ul>
                    <li><a href="#"></a></li>
                    <li><a href="#"></a></li>
                </ul>
            </div>
            <div class="header__top__right__auth">
                <a href="#"><i class="fa fa-user"></i> </a>
            </div>
        </div>
        <nav class="humberger__menu__nav mobile-menu">
            <ul>
                <li class="active"><a href="./index.html"></a></li>
                <li><a href="./shop-grid.html"></a></li>
                <li><a href="#"></a>
                    <ul class="header__menu__dropdown">
                  
                    </ul>
                </li>
                <li><a href="./blog.html"></a></li>
                <li><a href="./contact.html"></a></li>
            </ul>
        </nav>
        <div id="mobile-menu-wrap"></div>
        <div class="header__top__right__social">
       
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
                                
                              
                            </ul>
                        </div>
                    </div>
                    <div class="col-lg-6">
                        <div class="header__top__right">
                            <div class="header__top__right__social">
                          
                            </div>
                            <div class="header__top__right__language">
                               
                                <div></div>
                                <span class="arrow_carrot-down"></span>
                                <ul>
                                    <li><a href="#"></a></li>
                                    <li><a href="#"></a></li>
                                </ul>
                            </div>
                            <div class="header__top__right__auth">
                                <a href="#"></a>
                            </div>
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
                            <li><a href="./index.html"></a></li>
                            <li class="active"><a href="./shop-grid.html"></a></li>
                            <li><a href="#"></a>
                                <ul class="header__menu__dropdown">
                             
                                </ul>
                            </li>
                      
                        </ul>
                    </nav>
                </div>
                <div class="col-lg-3">
                    <div class="header__cart">
                        <ul>
                       
                        </ul>
                        <div class="header__cart__price"></div>
                    </div>
                </div>
            </div>
            <div class="humberger__open">
                <i class="fa fa-bars"></i>
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
                        <div class="hero__categories__all">

                        </div>
                        <ul>
                        </ul>
                    </div>
                </div>
                <div class="col-lg-9">
                    <div class="hero__search">
                        <div class="hero__search__form">
                        </div>
                        <div class="hero__search__phone">
                            <div class="hero__search__phone__icon">

                            </div>
                            <div class="hero__search__phone__text">
                            </div>
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
                        <h2>장바구니</h2>
                        <div class="breadcrumb__option">
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <!-- Breadcrumb Section End -->

    <!-- Product Details Section Begin -->
    <section class="product-details spad">
        <div class="container">
            <div class="row">
                <div class="col-lg-6 col-md-6">
                    <div class="product__details__pic">
                        <div class="product__details__pic__item">
                            <img src ="<spring:url value='/project0518/${productDetail.file_name_real}'/>" width = "300" height = "300"/>
                        </div>
                        <div class="product__details__pic__slider owl-carousel">
							<c:choose>
								<c:when test = '${empty prevWatchedList || fn:length(prevWatchedList)==1}' >
										이전에 본 상품이 없습니다.
								</c:when>
							<c:otherwise>
								<c:forEach var = "prevWatchedList" items = "${prevWatchedList}">
									<img src ="<spring:url value='/project0518/${prevWatchedList.file_name_real}'/>" />
										
								</c:forEach>
							</c:otherwise>
							</c:choose>
						
                        </div>
							<br><a href = "/product/ViewCart">장바구니 보러가기</a>
                    </div>
                </div>
                <div class="col-lg-6 col-md-6">
                    <div class="product__details__text">
                        <h3>${productDetail.product_name}</h3>
                        <div class="product__details__rating">
                            <i class="fa fa-star"></i>
                            <i class="fa fa-star"></i>
                            <i class="fa fa-star"></i>
                            <i class="fa fa-star"></i>
                            <i class="fa fa-star-half-o"></i>
                            <span></span>
                        </div>
                        
                        <div class="product__details__price">
                        <fmt:formatNumber  value="${productDetail.product_price * 0.6}" type="number" var="discount_price" />
                        	표준가격:${productDetail.product_price}<br>
                        	임직원 할인가:${discount_price}원
                        </div>
                        <label for="category">상품분류: </label>
                   	 			${productDetail.product_category} <br>
                        <div class="product__details__quantity">
                            <div class="quantity">
                               <strong><a href = "#" id = "incNum">+</a></strong>
                 					<span id = "number">1</span>
                 			   <strong><a href = "#" id ="decNum">-</a></strong>
                            </div>
                        </div>
                      <button type = "button" class= "cart" onclick="addCart(${productDetail.product_no})">장바구니 담기</button>
                        <ul>
                         
                        </ul>
                    </div>
                </div>
                <div class="col-lg-12">
                    <div class="product__details__tab">
                        
                        <div class="tab-content">
                            <div class="tab-pane active" id="tabs-1" role="tabpanel">
                                <div class="product__details__tab__desc">
                                 
                                </div>
                            </div>
                            <div class="tab-pane" id="tabs-2" role="tabpanel">
                                <div class="product__details__tab__desc">
                                    
                                </div>
                            </div>
                            <div class="tab-pane" id="tabs-3" role="tabpanel">
                                <div class="product__details__tab__desc">
                                   
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <!-- Product Details Section End -->

    <!-- Related Product Section Begin -->
    <section class="related-product">
        <div class="container">
            <div class="row">
              
            </div>
            <div class="row">
                
            </div>
        </div>
    </section>
    <!-- Related Product Section End -->

    <!-- Footer Section Begin -->
    <footer class="footer spad">
        <div class="container">
            <div class="row">
                <div class="col-lg-3 col-md-6 col-sm-6">
                    <div class="footer__about">
                        <div class="footer__about__logo">
                            <a href="./index.html"><img src="../resources/img/logo.png" alt=""></a>
                        </div>
                        <ul>
                            <li>Address: 60-49 Road 11378 New York</li>
                            <li>Phone: +65 11.188.888</li>
                            <li>Email: hello@colorlib.com</li>
                        </ul>
                    </div>
                </div>
                <div class="col-lg-4 col-md-6 col-sm-6 offset-lg-1">
                    <div class="footer__widget">
                        <h6>Useful Links</h6>
                        <ul>
                            <li><a href="#">About Us</a></li>
                            <li><a href="#">About Our Shop</a></li>
                            <li><a href="#">Secure Shopping</a></li>
                            <li><a href="#">Delivery infomation</a></li>
                            <li><a href="#">Privacy Policy</a></li>
                            <li><a href="#">Our Sitemap</a></li>
                        </ul>
                        <ul>
                            <li><a href="#">Who We Are</a></li>
                            <li><a href="#">Our Services</a></li>
                            <li><a href="#">Projects</a></li>
                            <li><a href="#">Contact</a></li>
                            <li><a href="#">Innovation</a></li>
                            <li><a href="#">Testimonials</a></li>
                        </ul>
                    </div>
                </div>
                <div class="col-lg-4 col-md-12">
                    <div class="footer__widget">
                        <h6>Join Our Newsletter Now</h6>
                        <p>Get E-mail updates about our latest shop and special offers.</p>
                        <form action="#">
                            <input type="text" placeholder="Enter your mail">
                            <button type="submit" class="site-btn">Subscribe</button>
                        </form>
                        <div class="footer__widget__social">
                            <a href="#"><i class="fa fa-facebook"></i></a>
                            <a href="#"><i class="fa fa-instagram"></i></a>
                            <a href="#"><i class="fa fa-twitter"></i></a>
                            <a href="#"><i class="fa fa-pinterest"></i></a>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-12">
                    <div class="footer__copyright">
                        <div class="footer__copyright__text"><p><!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->
  Copyright &copy;<script>document.write(new Date().getFullYear());</script> All rights reserved  <i class="fa fa-heart" aria-hidden="true"></i> by <a href="/" target="_blank">주명훈</a>
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

</html>