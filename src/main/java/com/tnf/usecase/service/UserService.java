package com.tnf.usecase.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;

import com.tnf.usecase.model.Product;
import com.tnf.usecase.model.Product.Currency;

public interface UserService {

	List<Product> getProducts(Currency currency, PageRequest pageRequest);
}
