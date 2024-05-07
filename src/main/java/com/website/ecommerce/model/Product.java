package com.website.ecommerce.model;

import java.util.Base64;
import java.util.List;


import jakarta.persistence.*;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Integer id;

    @Column(name = "product_brand")
    private String brand;
    
    @Column(name = "product_description")
    private String description;
    
    @Column(name = "product_price")
    private Double price;
    
    @Lob
    @Column(name = "product_image", columnDefinition = "longblob")
    private byte[] image;
    
    @Column(name = "product_rating")
    private Integer rating;
    
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    
    @ManyToOne
    @JoinColumn(name = "color_id")
    private Color color;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Size> sizes;
    
    public Product() {
    }

    public Product(Integer id, String brand, String description, Double price, byte[] image, Integer rating,
            Category category, Color color, List<Size> sizes) {
        this.id = id;
        this.brand = brand;
        this.description = description;
        this.price = price;
        this.image = image;
        this.rating = rating;
        this.category = category;
        this.color = color;
        this.sizes = sizes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public List<Size> getSizes() {
        return sizes;
    }

    public void setSizes(List<Size> sizes) {
        this.sizes = sizes;
    }

    public String getImageBase64() {
        if (image == null) {
            return null;
        }

        return Base64.getEncoder().encodeToString(image);
    }
}
