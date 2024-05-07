package com.website.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.website.ecommerce.model.OrderItem;
import com.website.ecommerce.model.OrderItemPk;

public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemPk> {

    List<OrderItem> findByOrderId(Integer orderId);
}
