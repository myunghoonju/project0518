package com.ddc2.project0518;


import java.sql.Timestamp;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.ddc2.project0518.model.UserRegister;
import com.ddc2.project0518.mybatis.UserDAO;

@Service
public class UserBO {

	@Inject
	UserDAO userDAO;
	
	private static final String AUTH = "ROLE_USER";
	
	public UserRegister signin(UserRegister signinInfo) {
		return userDAO.signin(signinInfo);
	}
	
	public boolean keepSignin(String userid, String sessionid, Timestamp sessionlimit) {
		return userDAO.keepSignin(userid,sessionid,sessionlimit);
	}
	public boolean insertUser(UserRegister userInfo) {
		UserRegister userInfoSet = new UserRegister();
		userInfoSet.setUserid(userInfo.getUserid());
		userInfoSet.setPassword(userInfo.getPassword());
		userInfoSet.setName(userInfo.getName());
		userInfoSet.setEmail(userInfo.getEmail());
		userInfoSet.setAuth(AUTH);
		
		return userDAO.insertUser(userInfoSet);
	}
	
	public List<UserRegister> getUserList(){
		UserRegister userInfo = new UserRegister();
		userInfo.setAuth(AUTH);
		return userDAO.getUserList(userInfo);
	}
	public UserRegister getUser(String userid) {
		return userDAO.getUser(userid);
	}
	public boolean updateUser(UserRegister userInfoNew) {
		return userDAO.updateUser(userInfoNew);
	}
	
}
