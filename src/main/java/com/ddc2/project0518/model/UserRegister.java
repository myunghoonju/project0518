package com.ddc2.project0518.model;

import java.sql.Date;
import java.sql.Timestamp;

import lombok.Data;

@Data
public class UserRegister {
	
	private String userid;
	private String password;
	private String name;
	private String email;
	private String auth; 
	private String sessionkey;
	private Timestamp sessionlimit;
	private Date register_date;
	private Date update_date;
	private boolean userCookie;
	

	public boolean isUserCookie() {
		return userCookie;
	}
}