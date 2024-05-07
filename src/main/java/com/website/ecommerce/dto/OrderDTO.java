package com.website.ecommerce.dto;

import java.util.List;

public class OrderDTO {
    private Integer id;
    private String orderDate;
    private Double totalAmount;
    private String customerPhone;
    private String customerEmail;
    private String customerName;
    private String status;
    private String address;
    private String additionalInformation;
    private String paymentMethod;
    private Integer customerId;
    private List<OrderItemDTO> orderItems;
    private List<OrderAnItemDTO> orderItemss;

    public String getCustomerEmail() {
        return customerEmail;
    }
    
    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderDate() {
        return orderDate;
    }
    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }
    
  
    public String getAdditionalInformation() {
        return additionalInformation;
    }
    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }
    public String getPaymentMethod() {
        return paymentMethod;
    }
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    public Integer getCustomerId() {
        return customerId;
    }
    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
    public List<OrderItemDTO> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemDTO> orderItems) {
        this.orderItems = orderItems;
    }

    public List<OrderAnItemDTO> getOrderItemss() {
        return orderItemss;
    }

    public void setOrderItemss(List<OrderAnItemDTO> orderItemss) {
        this.orderItemss = orderItemss;
    }

    public void addOrderItem(OrderAnItemDTO orderItem) {
        this.orderItemss.add(orderItem);
    }
}
