package com.website.ecommerce.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.website.ecommerce.repository.CustomerRepository;
import com.website.ecommerce.model.*;

@Service
public class CustomerService {

    @Autowired
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getCustomers() {
        List<Customer> customers = new ArrayList<Customer>();
        customerRepository.findAll().forEach(customers ::add);

        return customers;
    }

    public long countCustomers() {
        return customerRepository.count();
    }    

    public Optional<Customer> getCustomersByName(String username) {
        // Retrieve customers by username from the repository
        Optional <Customer> customers = customerRepository.findByUsername(username);
        
        // Return the list of customers
        return customers;
    }

    public Optional<Customer> getCustomerById(Long customerId) {
        return customerRepository.findById(customerId);
    }

    
    public void updateRole(String username) {
        // Update the role of the customer
        customerRepository.updateRoleByUsername(username);
    }
    
    public Customer addCustomer(Customer customer) {
        customerRepository.save(customer);
        return customer;
    }

    public Customer findByEmailAndPassword(String email, String password) {
        return customerRepository.findByEmailAndPassword(email, password);
    }

    public List<Customer> findNewCustomers() {
        return customerRepository.findNewCustomers();
    }

    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public void deleteCustomer(Customer customer) {
         customerRepository.delete(customer);
    }
}
