package com.website.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.website.ecommerce.service.CustomerService;
import com.website.ecommerce.service.JwtService;
import com.website.ecommerce.service.OrderService;
import com.website.ecommerce.service.ProductService;

import jakarta.servlet.http.HttpSession;

import com.website.ecommerce.dto.ProductDTO;
import com.website.ecommerce.model.Customer;
import com.website.ecommerce.model.Product;
import com.website.ecommerce.model.Role;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {

    private final JwtService jwtService;
    private final CustomerService cs;

        public ProductController(JwtService jwtService, CustomerService cs,
            OrderService orderService, HttpSession httpSession) {
        this.jwtService = jwtService;
        this.cs = cs;
        this.httpSession = httpSession;
    }

    @Autowired
    private HttpSession httpSession;

    @Autowired
    private ProductService productService;

    @GetMapping("/get-some-products")
    @ResponseBody
    public List<ProductDTO> getNumberOfProducts(Model model) {
        List<Product> products = productService.getNumberOfProducts(12);
        List<ProductDTO> productDTOs = productService.getProductDTOs(products);
        
        return productDTOs;
    }

    @GetMapping("/get-all-products")
    @ResponseBody
    public List<ProductDTO> getProducts() {
        List<Product> products = productService.getProducts();
        List<ProductDTO> productDTOs = productService.getProductDTOs(products);

        return productDTOs;
    }
    
    @GetMapping("/get-distinct-brands")
    @ResponseBody
    public List<String> getDistinctBrands() {
        return productService.getDistinctBrands();
    }

    @GetMapping("/get-products-data")
    public List<ProductDTO> getProductsData() {
        List<Product> products = productService.getProductsData();
        List<ProductDTO> productDTOs = new ArrayList<>();


        String token1 = (String) httpSession.getAttribute("jwtToken");
        String username = jwtService.extractUsername(token1);
        Optional<Customer> customerOptional = cs.getCustomersByName(username);
        Customer customer = customerOptional.get();
            if (customer.getRole() == Role.ADMIN)
            {
        for (Product product : products) {
            ProductDTO productDTO = new ProductDTO();
            productDTO.setId(product.getId());
            productDTO.setBrand(product.getBrand());
            productDTO.setDescription(product.getDescription().substring(0, 62).concat("..."));
            productDTO.setPrice(product.getPrice());
            productDTO.setRating(product.getRating());
            productDTOs.add(productDTO);
        }
            return productDTOs;
            }
            else{
                ProductDTO productDTO = new ProductDTO();
                productDTOs.add(productDTO);
                return productDTOs;
            }
    }

    @GetMapping("/get-product/{id}")
    @ResponseBody
    public ProductDTO getProductById(@PathVariable Integer id) {
        Product product = productService.getProductById(id);
        if (product == null) {
            // Handle the case where the product is not found
            return null;
        }

        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setBrand(product.getBrand());
        productDTO.setDescription(product.getDescription());
        productDTO.setPrice(product.getPrice());
        productDTO.setImageUrl(product.getImageBase64());
        productDTO.setRating(product.getRating());
        return productDTO;
    }

    @GetMapping("/count-products")
    public Long countProducts() {
        String token1 = (String) httpSession.getAttribute("jwtToken");
        if(token1 != null)
        {
            String username = jwtService.extractUsername(token1);
        
        // Use the repository to fetch data based on the username
        Optional<Customer> customerOptional = cs.getCustomersByName(username);
        
            Customer customer1 = customerOptional.get();
            if (customer1.getRole() == Role.ADMIN)
            {
                Long productCount = productService.countProducts();
                return productCount;
            }
            else
                return 0L;
        }
        else
                return 0L;
    }
}
