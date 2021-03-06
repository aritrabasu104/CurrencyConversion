package com.tnf.usecase.config;

import java.lang.reflect.Method;
import java.time.LocalDate;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import com.fasterxml.jackson.databind.ObjectMapper;


@Configuration
public class AppConfig {


	@Lazy
	@Autowired
	ObjectMapper mapper;
	
	
	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper;
	}
	@Bean("rateKeyGenerator")
    public KeyGenerator keyGenerator() {
        return new RateKeyGenerator();
    }
}
class RateKeyGenerator implements KeyGenerator {
	 
	@Override
	public Object generate(Object target, Method method, Object... params) {
		return LocalDate.now();
	}
}