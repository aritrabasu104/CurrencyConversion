package com.tnf.usecase.service;

import java.net.URI;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.tnf.usecase.dto.CurrencyRateResponseDto;

@FeignClient(name="currencyService",url = "http://data.fixer.io/")
public interface CurrencyService {

	@GetMapping("")
	@Cacheable(value = "rates",keyGenerator = "rateKeyGenerator")
	public CurrencyRateResponseDto getCurrencyRates(URI baseUri);
}
