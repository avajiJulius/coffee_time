package com.coffietime.dao;

import com.coffietime.domain.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepo extends CrudRepository<Product, Long> {
    List<Product> findByProductName(String productName);
}
