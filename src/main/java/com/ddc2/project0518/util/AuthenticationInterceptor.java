package com.ddc2.project0518.util;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

import com.ddc2.project0518.model.UserRegister;
import com.ddc2.project0518.mybatis.UserDAO;

/**
 컨트롤러보다 먼저 수행하는 클래스 입니다.
 * */
public class AuthenticationInterceptor extends HandlerInterceptorAdapter{

	@Inject
	UserDAO userDAO;
	
	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception{
		HttpSession session = req.getSession();
		Object obj = session.getAttribute("signin");
		
		if(obj == null) {
			Cookie signinCookie = WebUtils.getCookie(req, "signinCookie");
			if(signinCookie != null) {
				String sessionid = signinCookie.getValue();
				UserRegister user = userDAO.checkUserWithSessionKey(sessionid);
				
				if(user != null) {
					session.setAttribute("signin", user);
					return true;
				}
			}
			res.sendRedirect("/users/signin");
			return false; //HomeController로 가지않습니다.
		}
		return true; //HomeController로 이동합니다.
	}
	
	@Override
	public void postHandle(HttpServletRequest req, HttpServletResponse res, Object handler, ModelAndView mv) throws Exception{
		super.postHandle(req, res, handler, mv);
		
	}
	
	
}
