package com.website.ecommerce.model;

import jakarta.persistence.*;

@Entity
@Table(name = "order_items")
public class OrderItem {

    @EmbeddedId
    private OrderItemPk id;

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
  
    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
  
    @Column(name = "product_quantity")
    private Integer quantity;
  
    @Column(name = "product_status")
    private String status;

    public OrderItem() {

    }
    
    public OrderItem(Integer orderId, Integer productId, String status, Integer quantity) {
        this.id = new OrderItemPk(orderId, productId);
        this.status = status;
        this.quantity = quantity;
    }

    public OrderItemPk getId() {
        return id;
    }

    public void setId(OrderItemPk id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    
}
