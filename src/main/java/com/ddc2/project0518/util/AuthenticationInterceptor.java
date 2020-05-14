package com.ddc2.project0518.util;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import com.ddc2.project0518.mybatis.UserDAO;

public class AuthenticationInterceptor extends HandlerInterceptorAdapter{

	@Inject
	UserDAO userDAO;
	
	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception{
		HttpSession session = req.getSession();
		Object obj = session.getAttribute("signin");
		
		if(obj == null) {
			res.sendRedirect("/users/signin");
			return false; 
			
		}
		return true; 
	}
			
	@Override
	public void postHandle(HttpServletRequest req, HttpServletResponse res, Object handler, ModelAndView mv) throws Exception{
		super.postHandle(req, res, handler, mv);
		
	}
	
	
}