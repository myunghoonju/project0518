package com.ddc2.project0518.util;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ddc2.project0518.model.UserRegister;
import com.ddc2.project0518.mybatis.UserDAO;


public class AuthorizationInterceptor extends HandlerInterceptorAdapter{

	@Inject
	UserDAO userDAO;
	
	
	private static final String ADMIN = "ROLE_ADMIN";
	private static final String USER = "ROLE_USER";
	
	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception{
		HttpSession session = req.getSession();
		UserRegister signedin = (UserRegister)session.getAttribute("signin");
		String whoauth	= signedin.getAuth();
		if(signedin != null) {
			if(whoauth.contains(ADMIN)) {
				return true;
			}else if(whoauth.contains(USER)){
				ModelAndView model = new ModelAndView();
				model.setViewName("redirect:/index");
				model.addObject("message", "관리자메뉴입니다.");
				throw new ModelAndViewDefiningException(model);
			}else {
				//+추가등급?!
			}
		}
		
		return true; 
	}
			
	@Override
	public void postHandle(HttpServletRequest req, HttpServletResponse res, Object handler, ModelAndView mv) throws Exception{
		super.postHandle(req, res, handler, mv);		
	}
}
