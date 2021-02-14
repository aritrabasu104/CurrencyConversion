package com.tnf.usecase.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tnf.usecase.model.Product;
import com.tnf.usecase.model.Product.Currency;
import com.tnf.usecase.service.UserService;


@RestController
@Validated
@RequestMapping("/api/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/product")
	public ResponseEntity<List<Product>> getProducts(@RequestParam(required = true) Integer pageNumber,
			@RequestParam(required = true) Integer pageSize,
			@RequestParam(required = true)Currency currency){
		return ResponseEntity.ok(userService.getProducts(currency, PageRequest.of(pageNumber, pageSize)));
	}
	
}
