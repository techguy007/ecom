package com.website.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.website.ecommerce.repository.ProductRepository;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.website.ecommerce.model.Product;
import com.website.ecommerce.dto.ProductDTO;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    private static final Logger logger = Logger.getLogger(ProductService.class.getName());

    public List<Product> getProducts() {
        List<Product> products = productRepository.findAll();

        return products;
    }

    public List<Product> getNumberOfProducts(int numberOfProducts) {
        Pageable pageable = PageRequest.of(0, numberOfProducts);
        List<Product> products = productRepository.findAll(pageable).getContent();

        return products;
    }

    public List<ProductDTO> getProductDTOs(List<Product> products) {
        List<ProductDTO> productDTOs = new ArrayList<>();
        for (Product product : products) {
            ProductDTO productDTO = new ProductDTO();
            productDTO.setId(product.getId());
            productDTO.setBrand(product.getBrand());
            try {
                productDTO.setDescription(product.getDescription().substring(0, Math.min(product.getDescription().length(), 62)).concat("..."));
            } catch (StringIndexOutOfBoundsException e) {
                // Handle case where description is too short
                productDTO.setDescription(product.getDescription());
                logger.warning("Description is too short for product with ID: " + product.getId());
            } catch (Exception e) {
                // Handle other exceptions
                logger.log(Level.SEVERE, "Error processing description for product with ID: " + product.getId(), e);
            }
            productDTO.setPrice(product.getPrice());
            try {
                productDTO.setImageUrl(product.getImageBase64());
            } catch (Exception e) {
                // Handle other exceptions
                logger.log(Level.SEVERE, "Error processing image URL for product with ID: " + product.getId(), e);
            }
            productDTO.setRating(product.getRating());
            productDTOs.add(productDTO);
        }
        return productDTOs;
    }

    public List<String> getDistinctBrands() {
        return productRepository.findDistinctBrands();
    }

    public List<Product> getProductsData() {
        return productRepository.findDistinctProducts();
    }

    public Product getProductById(Integer id) {
        return productRepository.findById(id).orElse(null);
    }

    public long countProducts() {
        return productRepository.count();
    }
}
