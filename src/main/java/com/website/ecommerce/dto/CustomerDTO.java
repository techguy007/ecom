package com.website.ecommerce.dto;

import com.website.ecommerce.model.Role;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class CustomerDTO {

    private Integer id;
    private String name;
    private String email;
    private String password;
    private String phone;
    @Enumerated(value = EnumType.STRING)
    Role role;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    
}
