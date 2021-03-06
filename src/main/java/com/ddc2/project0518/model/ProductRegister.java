package com.ddc2.project0518.model;

import java.io.Serializable;
import java.sql.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ProductRegister implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int product_no;
	@NotEmpty
	private String product_name;
	@NotNull
    private int product_price;
	@NotEmpty
	private String product_category;
	private String file_name;
    private String file_name_real;
    private int file_size;
    private Date register_date;
    private Date update_date;
}
