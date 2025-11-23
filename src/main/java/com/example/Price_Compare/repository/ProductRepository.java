package com.example.Price_Compare.repository;

import com.example.Price_Compare.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // you can add custom queries later
}
