package com.tnf.usecase.controller;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tnf.usecase.dto.ProductRequestDto;
import com.tnf.usecase.model.Product;
import com.tnf.usecase.service.AdminService;


@RestController
@RequestMapping("/api/admin")
public class AdminController {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private AdminService adminService;
	
	@PostMapping("/product")
	public ResponseEntity<Product> register(@Valid @RequestBody ProductRequestDto productDto){
		Product product = modelMapper.map(productDto, Product.class);
		return ResponseEntity.ok(adminService.addProduct(product));
	}
	
}
