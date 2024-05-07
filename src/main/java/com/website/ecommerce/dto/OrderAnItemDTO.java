package com.website.ecommerce.dto;

public class OrderAnItemDTO {
    private Integer quantity;
    private Double price;
    private String imageBase64;

    // Constructors, getters, and setters

    public OrderAnItemDTO() {
    }

    public OrderAnItemDTO(Integer quantity, Double price, String imageBase64) {
        this.quantity = quantity;
        this.price = price;
        this.imageBase64 = imageBase64;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }
}
