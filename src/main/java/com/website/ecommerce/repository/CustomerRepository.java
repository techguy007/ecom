package com.website.ecommerce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
// import org.springframework.data.repository.CrudRepository;

import com.website.ecommerce.model.Customer;

import jakarta.transaction.Transactional;

public interface CustomerRepository extends JpaRepository<Customer, Integer>{

    Optional<Customer> findByUsername(String username);
    
    // Customer findByUsername (String username);

    Customer findByEmailAndPassword(String customer_email, String customer_password);

    Optional<Customer> findById(Long id);
    
    @Query("SELECT c FROM Customer c ORDER BY c.id DESC LIMIT 4")
    List<Customer> findNewCustomers();

    @Query("SELECT c FROM Customer c ORDER BY c.id")
    List<Customer> findAllNewCustomers();

    @Transactional
    @Modifying
    @Query("UPDATE Customer c SET c.role = Role.ADMIN WHERE c.username = :username")
    void updateRoleByUsername(String username);

    boolean existsByEmail(String email);
}

