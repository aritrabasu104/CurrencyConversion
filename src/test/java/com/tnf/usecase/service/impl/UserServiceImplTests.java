package com.tnf.usecase.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.util.ReflectionTestUtils;

import com.tnf.usecase.dto.CurrencyRateResponseDto;
import com.tnf.usecase.error.custom.CurrencyServiceCommunicationException;
import com.tnf.usecase.error.custom.IncompleteConversionMapException;
import com.tnf.usecase.error.custom.InvalidConversionRateException;
import com.tnf.usecase.model.Product;
import com.tnf.usecase.model.Product.Currency;
import com.tnf.usecase.repository.ProductRepository;
import com.tnf.usecase.service.CurrencyService;

import feign.FeignException;

@SpringBootTest
public class UserServiceImplTests {

	@Value("${conversion.api.uri}")
	private String CONVERSION_URI;

	@Mock
	private ProductRepository productRepository;

	@Mock
	private CurrencyService currencyService;

	@InjectMocks
	private UserServiceImpl userServiceImpl;

	@Captor
	ArgumentCaptor<PageRequest> acPageRequest;

	@Captor
	ArgumentCaptor<URI> acUri;

	private EasyRandom generator = new EasyRandom();
	private List<Product> products;
	private Page<Product> productsPage;
	private CurrencyRateResponseDto currencyRateResponseDto = new CurrencyRateResponseDto();
	private Map<String, Double> rates;
	
	@BeforeEach
	public void init() {
		products = new ArrayList<>();
		Product product = generator.nextObject(Product.class);
		product.setCurrency(Currency.INR);
		product.setPrice(BigDecimal.valueOf(240));
		products.add(product);
		product = generator.nextObject(Product.class);
		product.setCurrency(Currency.EUR);
		product.setPrice(BigDecimal.valueOf(4));
		products.add(product);

		productsPage = new PageImpl<>(products, PageRequest.of(0, 2), 2);

		rates = new HashMap<>();
		rates.put(Currency.INR.toString(), 80d);
		rates.put(Currency.EUR.toString(), 1d);
		currencyRateResponseDto.setRates(rates);
		
		ReflectionTestUtils.setField(userServiceImpl, "CONVERSION_URI", CONVERSION_URI);
		ReflectionTestUtils.setField(userServiceImpl, "CONVERSION_SCALE", 10);
	}

	@Test
	public void shouldReturnProuductsWithConvertedPrice() {
		
		PageRequest pageRequest = PageRequest.of(0, 5);
		when(productRepository.findAll(pageRequest)).thenReturn(productsPage);
		when(currencyService.getCurrencyRates(URI.create(CONVERSION_URI))).thenReturn(currencyRateResponseDto);

		List<Product> actualResult = userServiceImpl.getProducts(Currency.INR, pageRequest);
		List<Product> expectedResult = new ArrayList<>();
		
		
		Product product = new Product();
		product.setId(products.get(0).getId());
		product.setCurrency(Currency.INR);
		product.setPrice(BigDecimal.valueOf(240));
		product.setName(products.get(0).getName());
		product.setDescription(products.get(0).getDescription());
		expectedResult.add(product);
		
		product = new Product();
		product.setId(products.get(1).getId());
		product.setCurrency(Currency.INR);
		product.setPrice(BigDecimal.valueOf(320));
		product.setName(products.get(1).getName());
		product.setDescription(products.get(1).getDescription());
		expectedResult.add(product);
		
		assertEquals(expectedResult, actualResult);
		for(int i=0;i<expectedResult.size();i++)
			assertEquals(0,actualResult.get(i).getPrice().compareTo(expectedResult.get(i).getPrice()));
		
		verify(productRepository,times(1)).findAll(acPageRequest.capture());
		assertEquals(pageRequest, acPageRequest.getValue());
		verify(currencyService,times(1)).getCurrencyRates(acUri.capture());
		assertEquals(URI.create(CONVERSION_URI), acUri.getValue());

	}
	
	@Test
	public void shouldThrowCurrencyServiceCommunicationException() {
		
		PageRequest pageRequest = PageRequest.of(0, 5);
		when(productRepository.findAll(pageRequest)).thenReturn(productsPage);
		when(currencyService.getCurrencyRates(URI.create(CONVERSION_URI))).thenThrow(FeignException.class);
		
		assertThrows(CurrencyServiceCommunicationException.class, () -> userServiceImpl.getProducts(Currency.INR, pageRequest));	

	}
	
	@Test
	public void shouldThrowInvalidConversionRateException() {
		
		PageRequest pageRequest = PageRequest.of(0, 5);
		when(productRepository.findAll(pageRequest)).thenReturn(productsPage);
		
		rates = new HashMap<>();
		rates.put(Currency.INR.toString(), 0d);
		rates.put(Currency.EUR.toString(), 0d);
		currencyRateResponseDto.setRates(rates);
		when(currencyService.getCurrencyRates(URI.create(CONVERSION_URI))).thenReturn(currencyRateResponseDto);
		
		assertThrows(InvalidConversionRateException.class, () -> userServiceImpl.getProducts(Currency.INR, pageRequest));	

	}
	

	@Test
	public void shouldThrowIncompleteConversionMapException() {
		
		PageRequest pageRequest = PageRequest.of(0, 5);
		when(productRepository.findAll(pageRequest)).thenReturn(productsPage);
		
		rates = new HashMap<>();
		rates.put(Currency.INR.toString(), 0d);
		currencyRateResponseDto.setRates(rates);
		when(currencyService.getCurrencyRates(URI.create(CONVERSION_URI))).thenReturn(currencyRateResponseDto);
		
		assertThrows(IncompleteConversionMapException.class, () -> userServiceImpl.getProducts(Currency.INR, pageRequest));	

	}
}
