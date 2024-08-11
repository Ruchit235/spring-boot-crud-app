package com.prod.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prod.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}

