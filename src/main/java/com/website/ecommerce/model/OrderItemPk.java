package com.website.ecommerce.model;

import jakarta.persistence.*;

@Embeddable
public class OrderItemPk {
    private Integer orderId;
    private Integer productId;

    public OrderItemPk() {

    }

    public OrderItemPk(Integer orderId, Integer productId) {
        this.orderId = orderId;
        this.productId = productId;
    }
    
    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }
}
