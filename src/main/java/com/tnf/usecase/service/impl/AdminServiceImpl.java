package com.tnf.usecase.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tnf.usecase.model.Product;
import com.tnf.usecase.repository.ProductRepository;
import com.tnf.usecase.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private ProductRepository productRepository;

	@Override
	public Product addProduct(Product product){
		return productRepository.save(product);
	}

}
