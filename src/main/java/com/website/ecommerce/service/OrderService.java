package com.website.ecommerce.service;

import java.util.List;
import java.util.Optional;
import java.util.logging.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.website.ecommerce.dto.OrderDTO;
import com.website.ecommerce.dto.OrderItemDTO;
import com.website.ecommerce.model.Customer;
import com.website.ecommerce.model.Order;
import com.website.ecommerce.model.OrderItem;
import com.website.ecommerce.model.OrderItemPk;
import com.website.ecommerce.model.Product;
import com.website.ecommerce.repository.CustomerRepository;
import com.website.ecommerce.repository.OrderItemRepository;
import com.website.ecommerce.repository.OrderRepository;
import com.website.ecommerce.repository.ProductRepository;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ProductRepository productRepository;

    private static final Logger logger = Logger.getLogger(OrderService.class.getName());

    public List<Order> getRecentOrders() {
        return orderRepository.findTopNByOrderByOrderDateDesc();
    }

    public Order placeOrder(OrderDTO orderDTO) {
        try {
            Order order = new Order();
            order.setOrderDate(orderDTO.getOrderDate());
            order.setTotalAmount(orderDTO.getTotalAmount());
            order.setAddress(orderDTO.getAddress());
            order.setAdditionalInformation(orderDTO.getAdditionalInformation());
            order.setPaymentMethod(orderDTO.getPaymentMethod());

            Customer customer = customerRepository.findById(orderDTO.getCustomerId()).orElse(null);
            order.setCustomer(customer);

            order = orderRepository.save(order);

            for (OrderItemDTO orderItemDTO : orderDTO.getOrderItems()) {
                OrderItem orderItem = new OrderItem();
                OrderItemPk orderItemPk = new OrderItemPk();
                orderItemPk.setOrderId(order.getId());
                orderItemPk.setProductId(orderItemDTO.getProductId());
                orderItem.setId(orderItemPk);
                orderItem.setOrder(order);
                Product product = productRepository.findById(orderItemDTO.getProductId()).orElse(null);
                orderItem.setProduct(product);
                orderItem.setQuantity(orderItemDTO.getQuantity());
                orderItem.setStatus("Out for Delivery");

                orderItemRepository.save(orderItem);
            }

            return order;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred while placing the order: " + e.getMessage(), e);
            // Handle exception
            return null;
        }
    }

    public List<Order> getAllOrdersByCustomerId(Long customerId) {
        return orderRepository.findAllByCustomer_Id(customerId);
    }

    public long countOrders() {
        return orderRepository.count();
    }

    public long countEarnings() {
        return orderRepository.sumOrderTotalAmount();
    }

    public Order save(Order order) {
        // Save the order
        Order savedOrder = orderRepository.save(order);
        return savedOrder;
    }

    public List<Order> getAllRecentOrders() {
        return orderRepository.findAllOrders();
    }

    public Optional<Order> getOrderById(Integer orderId) {
        return orderRepository.findById(orderId);
    }

    public void saveOrder(Order order) {
        orderRepository.save(order);
    }
}
