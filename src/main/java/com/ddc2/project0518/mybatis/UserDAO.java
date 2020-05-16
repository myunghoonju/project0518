package com.ddc2.project0518.mybatis;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.ddc2.project0518.model.UserRegister;

@Repository
public class UserDAO {

	@Resource(name = "sqlSessionCommon")
	private SqlSession sqlSession;
	
	private static final String NAMESPACE = "com.ddc2.project0518.mybatis.User.";

	
	public UserRegister signin(UserRegister signinInfo) {
		return sqlSession.selectOne(NAMESPACE + "signin",signinInfo);
	}

	public boolean keepSignin(String userid, String sessionid, Timestamp sessionlimit) {
		Map<String, Object> map =  new HashMap<String, Object>();
		map.put("userid", userid);
		map.put("sessionid", sessionid);
		map.put("sessionlimit", sessionlimit);
		return sqlSession.update(NAMESPACE + "keepSignin", map) == 1 ? true:false;
	}
	
	public UserRegister checkUserWithSessionKey(String sessionid) {
		return sqlSession.selectOne(NAMESPACE + "checkUserWithSessionKey", sessionid);
	}
	
	public boolean insertUser(UserRegister userInfoSet) {
		return sqlSession.insert(NAMESPACE + "insertUser", userInfoSet) == 1 ? true:false;
	}
	public List<UserRegister> getUserList(UserRegister userInfo) {
		return sqlSession.selectList(NAMESPACE + "getUserList",userInfo);
	}
	public UserRegister getUser(String userid) {
		return sqlSession.selectOne(NAMESPACE + "getUser", userid);
	}
	public boolean updateUser(UserRegister userInfoNew) {
		return sqlSession.update(NAMESPACE + "updateUser", userInfoNew) == 1 ? true:false;
	}
	
}
