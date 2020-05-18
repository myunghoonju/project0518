package com.ddc2.project0518.model;

import javax.validation.constraints.NotEmpty;


import lombok.Data;

@Data
public class UpdateUserInfoValidator {
	
	private String userid;
	@NotEmpty
	private String name;
	@NotEmpty
	private String email;
	@NotEmpty
	private String auth; 
}