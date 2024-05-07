package com.website.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.website.ecommerce.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>{
    
}
