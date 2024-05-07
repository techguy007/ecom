package com.website.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.website.ecommerce.model.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    
    @Query("SELECT o FROM Order o ORDER BY o.orderDate DESC LIMIT 4")
    List<Order> findTopNByOrderByOrderDateDesc();

    @Query("SELECT o FROM Order o ORDER BY o.orderDate")
    List<Order> findAllOrders();

    @Query("SELECT SUM(o.totalAmount) FROM Order o")
    long sumOrderTotalAmount();

    List<Order> findAllByCustomer_Id(Long customerId);
}
