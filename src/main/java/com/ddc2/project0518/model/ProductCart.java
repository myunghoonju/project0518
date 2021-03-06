package com.ddc2.project0518.model;

import java.sql.Date;

import lombok.Data;

@Data
public class ProductCart {	
	private int cart_no;
	private int product_no;
	private String userid;
	private int amount;
	private Date register_date;
	private Date update_date;
}