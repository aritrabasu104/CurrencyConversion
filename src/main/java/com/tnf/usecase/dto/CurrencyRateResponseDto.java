package com.tnf.usecase.dto;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class CurrencyRateResponseDto {

	private Map<String,Double> rates;
	
}
