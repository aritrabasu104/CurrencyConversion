package com.tnf.usecase.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.tnf.usecase.error.custom.CurrencyServiceCommunicationException;
import com.tnf.usecase.error.custom.InvalidConversionRateException;
import com.tnf.usecase.model.Product;
import com.tnf.usecase.model.Product.Currency;
import com.tnf.usecase.repository.ProductRepository;
import com.tnf.usecase.service.CurrencyService;
import com.tnf.usecase.service.UserService;

import feign.FeignException;

@Service
public class UserServiceImpl implements UserService {

	@Value("${conversion.api.uri}")
	private String CONVERSION_URI;

	@Value("${conversion.scale}")
	private Integer CONVERSION_SCALE;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CurrencyService currencyService;

	@Override
	public List<Product> getProducts(Currency currency, PageRequest pageRequest){
		Map<String, Double> rates = getRates();
		return productRepository.findAll(pageRequest).stream().map(item -> {
			if (!item.getCurrency().equals(currency)) {
				BigDecimal priceInEuro;
				try {
					priceInEuro = item.getPrice().divide(BigDecimal.valueOf(rates.get(item.getCurrency().toString())),
							CONVERSION_SCALE, RoundingMode.HALF_DOWN);
				} catch (ArithmeticException e) {
					throw new InvalidConversionRateException(item.getCurrency().toString());
				}
				BigDecimal finalPrice = priceInEuro.multiply(BigDecimal.valueOf(rates.get(currency.toString())));
				item.setPrice(finalPrice);
				item.setCurrency(currency);
			}
			return item;
		}).collect(Collectors.toList());
	}

	private Map<String, Double> getRates(){
		try {
			return currencyService.getCurrencyRates(URI.create(CONVERSION_URI)).getRates();
		} catch (FeignException e) {
			throw new CurrencyServiceCommunicationException(e.getMessage());
		}

	}
}
