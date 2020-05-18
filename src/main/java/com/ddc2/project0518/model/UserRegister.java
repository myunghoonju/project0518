package com.ddc2.project0518.model;

import java.sql.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class UserRegister {
	
	@NotEmpty
	private String userid;
	@NotEmpty
	private String password;
	@NotEmpty
	private String name;
	@Pattern(regexp = "^.+@kr.doubledown.com")
	private String email;
	private String auth; 
	private Date register_date;
	private Date update_date;

}