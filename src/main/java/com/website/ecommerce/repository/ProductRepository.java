package com.website.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.website.ecommerce.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>{
    @Query("SELECT DISTINCT p.brand FROM Product p")
    List<String> findDistinctBrands();

    @Query(value = "SELECT * FROM product WHERE (product_id - 1) % 4 = 0 LIMIT 8", nativeQuery = true)
    List<Product> findDistinctProducts();
}
