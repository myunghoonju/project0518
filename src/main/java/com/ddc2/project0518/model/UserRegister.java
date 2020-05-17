package com.ddc2.project0518.model;

import java.sql.Date;

import lombok.Data;

@Data
public class UserRegister {
	
	private String userid;
	private String password;
	private String name;
	private String email;
	private String auth; 
	private Date register_date;
	private Date update_date;

}