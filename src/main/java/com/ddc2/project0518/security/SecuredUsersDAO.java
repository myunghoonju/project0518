package com.ddc2.project0518.security;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@Repository
public class SecuredUsersDAO {

	@Resource(name = "sqlSessionCommon")
	private SqlSession sqlSession;
	
	private static final String NAMESPACE = "com.ddc2.project0518.security.Security.";

	public SecuredUsers getUser(String username) {
		return sqlSession.selectOne(NAMESPACE + "getUser", username);
	}
}
