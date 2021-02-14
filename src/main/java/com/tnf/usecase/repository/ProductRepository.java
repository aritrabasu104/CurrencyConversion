package com.tnf.usecase.repository;

import java.util.UUID;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.tnf.usecase.model.Product;

public interface ProductRepository extends PagingAndSortingRepository<Product, UUID> {

}
