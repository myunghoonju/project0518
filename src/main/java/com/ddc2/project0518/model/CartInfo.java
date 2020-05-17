package com.ddc2.project0518.model;

import java.sql.Date;

import lombok.Data;

@Data
public class CartInfo {	
	private int cart_no;
	private int product_no;
	private String userid;
	private int amount;
	private Date register_date;
	private String file_name_real;
	private String product_name;
	private int product_price;
	

}