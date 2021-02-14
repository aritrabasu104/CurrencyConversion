package com.tnf.usecase.dto;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import com.sun.istack.NotNull;
import com.tnf.usecase.model.Product.Currency;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class ProductRequestDto {

	@NotBlank(message = "Please provide a name for product")
	private String name;
	
	@NotBlank(message = "Please provide a description for product")
	private String description;
	
	@Min(0)
	private BigDecimal price;
	
	@NotNull
	private Currency currency;
	
}
