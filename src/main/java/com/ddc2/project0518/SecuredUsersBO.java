package com.ddc2.project0518;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.ddc2.project0518.security.SecuredUsers;
import com.ddc2.project0518.security.SecuredUsersDAO;


@Component
public class SecuredUsersBO implements UserDetailsService{
	
	@Inject
	SecuredUsersDAO secDAO;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		SecuredUsers user = secDAO.getUser(username); //조회값 담기
		SecuredUsers securedUsers = new SecuredUsers(); //인증값 넣기
		
		if(user != null) {
			List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			authorities.add(new SimpleGrantedAuthority(user.getAuth()));
			securedUsers.setAuthorities(authorities);
		}
		
		
		
		return securedUsers;
	}
	
	
}
