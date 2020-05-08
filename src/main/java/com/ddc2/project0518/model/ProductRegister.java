package com.ddc2.project0518.model;

import java.io.Serializable;
import java.sql.Date;

import lombok.Data;

@Data
public class ProductRegister implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int product_no;
	private String product_name;
    private int product_price;
	private String product_category;
	private String file_name;
    private String file_name_real;
    private int file_size;
    private Date register_date;
    private Date update_date;
}
