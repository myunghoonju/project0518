package com.ddc2.project0518.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class UpdateProductInfoValidator{
	
	private int product_no;
	@NotEmpty
	private String product_name;
	@NotNull
    private int product_price;
	@NotEmpty
	private String product_category;
}
