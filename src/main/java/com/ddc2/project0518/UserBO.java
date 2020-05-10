package com.ddc2.project0518;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import org.springframework.stereotype.Service;

import com.ddc2.project0518.model.UserRegister;
import com.ddc2.project0518.mybatis.UserDAO;

@Service
public class UserBO {

	@Inject
	UserDAO userDAO;
	
	
	public UserRegister Signin(UserRegister signinInfo) {
		return userDAO.signin(signinInfo);
	}
	
	public boolean keepSignin(String userid, String sessionid, Timestamp sessionlimit) {
		return userDAO.keepSignin(userid,sessionid,sessionlimit);
	}
	
	
}
