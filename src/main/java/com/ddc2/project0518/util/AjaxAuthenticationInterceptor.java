package com.ddc2.project0518.util;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ddc2.project0518.model.UserRegister;
import com.ddc2.project0518.mybatis.UserDAO;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class AjaxAuthenticationInterceptor extends HandlerInterceptorAdapter{

	@Inject
	UserDAO userDAO;
	
	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception{
		HttpSession session = req.getSession();
		Object obj = session.getAttribute("signin");
		log.info("ajax인터셉터");
		if(obj == null) {
			if(testAjax(req)) {
				res.sendError(500);
				return false;
			}
		}
	
		return true;
	}
	private boolean testAjax(HttpServletRequest req) {
		String header = req.getHeader("AJAX");
		if(header.equals("true")) {
			return true;
		}else {
			return false;
		}
	}
	
	@Override
	public void postHandle(HttpServletRequest req, HttpServletResponse res, Object handler, ModelAndView mv) throws Exception{
		super.postHandle(req, res, handler, mv);
		
	}	
	
}