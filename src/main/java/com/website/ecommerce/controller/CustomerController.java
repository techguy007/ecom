package com.website.ecommerce.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
// import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.website.ecommerce.dto.CustomerDTO;
import com.website.ecommerce.model.Customer;
import com.website.ecommerce.model.Role;
import com.website.ecommerce.service.CustomerService;
import com.website.ecommerce.service.JwtService;
import com.website.ecommerce.service.OrderService;

import jakarta.servlet.http.HttpSession;

@RestController
public class CustomerController {

    private final JwtService jwtService;
    private final CustomerService cs;

    public CustomerController(JwtService jwtService, CustomerService cs,
            OrderService orderService, HttpSession httpSession) {
        this.jwtService = jwtService;
        this.cs = cs;
        this.httpSession = httpSession;
    }

    @Autowired
    private HttpSession httpSession;

    @Autowired
    private CustomerService customerService;

    @GetMapping("/get-new-customers") 
    public List<CustomerDTO> getNewCustomers() {

        List<Customer> customers = customerService.findNewCustomers();
        List<CustomerDTO> customerDTOs = new ArrayList<>();

        String token1 = (String) httpSession.getAttribute("jwtToken");
        String username = jwtService.extractUsername(token1);
        
        // Use the repository to fetch data based on the username
        Optional<Customer> customerOptional = cs.getCustomersByName(username);
        
            Customer customer1 = customerOptional.get();
            if (customer1.getRole() == Role.ADMIN)
            {
        for (Customer customer : customers) {
            CustomerDTO customerDTO = new CustomerDTO();
            customerDTO.setId(customer.getId());
            customerDTO.setName(customer.getName());
            customerDTO.setEmail(customer.getEmail());
            customerDTO.setPhone(customer.getPhone());
            customerDTOs.add(customerDTO);
            }
                return customerDTOs;
            }
            else 
            {
                CustomerDTO customerDTO = new CustomerDTO();
                customerDTOs.add(customerDTO);
                return customerDTOs;
            }
                 
    }

    @GetMapping("/get-customers")
    public List<CustomerDTO> getCustomers() {
        List<Customer> customers = customerService.getCustomers();
        List<CustomerDTO> customerDTOs = new ArrayList<>();
        
        String token1 = (String) httpSession.getAttribute("jwtToken");
        String username = jwtService.extractUsername(token1);
        
        // Use the repository to fetch data based on the username
        Optional<Customer> customerOptional = cs.getCustomersByName(username);
        
            Customer customer1 = customerOptional.get();
            if (customer1.getRole() == Role.ADMIN)
            {
                for (Customer customer : customers) {
                    CustomerDTO customerDTO = new CustomerDTO();
                    customerDTO.setId(customer.getId());
                    customerDTO.setName(customer.getName());
                    customerDTO.setEmail(customer.getEmail());
                    customerDTO.setPhone(customer.getPhone());
                    customerDTOs.add(customerDTO);
                }
                return customerDTOs;
            }
            else
            {
                List<CustomerDTO> customerDTOf = new ArrayList<>();
                CustomerDTO customerDTOz = new CustomerDTO();
                customerDTOf.add(customerDTOz);
                return customerDTOf;
            }
        
    }

    @GetMapping("/get-a-customer")
    public List<CustomerDTO> getCustomer() {
        List<CustomerDTO> customerDTOs = new ArrayList<>();
        
        // Get JWT token from session
        String token = (String) httpSession.getAttribute("jwtToken");
        
        // Extract username from JWT token
        String username = jwtService.extractUsername(token);
        
        // Use the repository to fetch data based on the username
        Optional<Customer> customerOptional = customerService.getCustomersByName(username);
        
        // Check if customer exists for the given username
        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            
                CustomerDTO customerDTO = new CustomerDTO();
                customerDTO.setId(customer.getId());
                customerDTO.setName(customer.getName());
                customerDTO.setEmail(customer.getEmail());
                customerDTO.setPhone(customer.getPhone());
                customerDTOs.add(customerDTO);

                return customerDTOs;
        }
        else
            return null;
    }


    @PatchMapping("/update-customer/{customerId}")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable Long customerId, @RequestBody CustomerDTO updatedCustomerDTO) {
            // Retrieve the customer from the database
            Optional<Customer> customerOptional = cs.getCustomerById(customerId);
            if (customerOptional.isPresent()) {
                Customer customer = customerOptional.get();
                // Update the customer's data
                customer.setName(updatedCustomerDTO.getName());
                customer.setEmail(updatedCustomerDTO.getEmail());
                customer.setPhone(updatedCustomerDTO.getPhone());
                // Save the updated customer
                customerService.saveCustomer(customer);
                // Construct and return the updated CustomerDTO
                CustomerDTO updatedCustomer = new CustomerDTO();
                updatedCustomer.setId(customer.getId());
                updatedCustomer.setName(customer.getName());
                updatedCustomer.setEmail(customer.getEmail());
                updatedCustomer.setPhone(customer.getPhone());
                return ResponseEntity.ok(updatedCustomer);
            } else {
                return ResponseEntity.notFound().build();
            }
        } 

    @DeleteMapping("/delete-customer/{customerId}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long customerId) {
    // Check if the authenticated user is an admin
    String token1 = (String) httpSession.getAttribute("jwtToken");
    String username = jwtService.extractUsername(token1);
    Optional<Customer> adminOptional = cs.getCustomersByName(username);
    
    if (adminOptional.isPresent()) {
        Customer admin = adminOptional.get();
        if (admin.getRole() == Role.ADMIN) {
            // Retrieve the customer from the database
            Optional<Customer> customerOptional = cs.getCustomerById(customerId);
            if (customerOptional.isPresent()) {
                Customer customer = customerOptional.get();
                // Delete the customer
                customerService.deleteCustomer(customer);
                return ResponseEntity.ok("Customer deleted successfully");
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            // User is not authorized to delete customer
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access");
        }
    } else {
        // Admin user not found
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access");
    }
}

// Java Controller
@PatchMapping("/update-a-customer/{customerId}")
public ResponseEntity<CustomerDTO> updateACustomer(@PathVariable Long customerId, @RequestBody CustomerDTO updatedCustomerDTO) {
    // Retrieve the customer from the database
    Optional<Customer> customerOptional = customerService.getCustomerById(customerId);
    if (customerOptional.isPresent()) {
        Customer customer = customerOptional.get();
        // Update the customer's data
        customer.setName(updatedCustomerDTO.getName());
        customer.setEmail(updatedCustomerDTO.getEmail());
        customer.setPhone(updatedCustomerDTO.getPhone());
        // Save the updated customer
        customerService.saveCustomer(customer);
        // Construct and return the updated CustomerDTO
        CustomerDTO updatedCustomer = new CustomerDTO();
        updatedCustomer.setId(customer.getId());
        updatedCustomer.setName(customer.getName());
        updatedCustomer.setEmail(customer.getEmail());
        updatedCustomer.setPhone(customer.getPhone());
        return ResponseEntity.ok(updatedCustomer);
    } else {
        return ResponseEntity.notFound().build();
    }
}

    @GetMapping("/get-roles")
    public List<CustomerDTO> getRole() {
        List<Customer> customers = customerService.findNewCustomers();
        List<CustomerDTO> customerDTOs = new ArrayList<>();

        String token1 = (String) httpSession.getAttribute("jwtToken");
        String username = jwtService.extractUsername(token1);
        
        // Use the repository to fetch data based on the username
        Optional<Customer> customerOptional = cs.getCustomersByName(username);
        
            Customer customer1 = customerOptional.get();
            if (customer1.getRole() == Role.ADMIN)
            {
        for (Customer customer : customers) {
            CustomerDTO customerDTO = new CustomerDTO();
            customerDTO.setId(customer.getId());
            customerDTO.setName(customer.getName());
            customerDTO.setRole(customer.getRole());
            // customerDTO.setEmail(customer.getEmail());
            // customerDTO.setPassword(customer.getPassword());
            // customerDTO.setPhone(customer.getPhone());
            customerDTOs.add(customerDTO);
        }

        return customerDTOs;
        }
        else
        {
            List<CustomerDTO> customerDTOf = new ArrayList<>();
            CustomerDTO customerDTOz = new CustomerDTO();
            customerDTOf.add(customerDTOz);
            return customerDTOf;
        }
    }

    @GetMapping("/get-all-roles")
    public List<CustomerDTO> getAllRole() {
        List<Customer> customers = customerService.getCustomers();
        List<CustomerDTO> customerDTOs = new ArrayList<>();

        String token1 = (String) httpSession.getAttribute("jwtToken");
        String username = jwtService.extractUsername(token1);
        
        // Use the repository to fetch data based on the username
        Optional<Customer> customerOptional = cs.getCustomersByName(username);
        
            Customer customer1 = customerOptional.get();
            if (customer1.getRole() == Role.ADMIN)
            {
        for (Customer customer : customers) {
            CustomerDTO customerDTO = new CustomerDTO();
            customerDTO.setId(customer.getId());
            customerDTO.setName(customer.getName());
            customerDTO.setRole(customer.getRole());
            // customerDTO.setEmail(customer.getEmail());
            // customerDTO.setPassword(customer.getPassword());
            // customerDTO.setPhone(customer.getPhone());
            customerDTOs.add(customerDTO);
        }

        return customerDTOs;
        }
        else
        {
            List<CustomerDTO> customerDTOf = new ArrayList<>();
            CustomerDTO customerDTOz = new CustomerDTO();
            customerDTOf.add(customerDTOz);
            return customerDTOf;
        }
    }

    @GetMapping("/count-users")
    public Long countUsers() {
        String token1 = (String) httpSession.getAttribute("jwtToken");
        if(token1 != null)
        {
            String username = jwtService.extractUsername(token1);
        
        // Use the repository to fetch data based on the username
        Optional<Customer> customerOptional = cs.getCustomersByName(username);
        
            Customer customer1 = customerOptional.get();
            if (customer1.getRole() == Role.ADMIN)
            {
                Long customersCount = customerService.countCustomers();
                return customersCount;
            }
            else
                return 0L;
        }
        else
                return 0L;
    }
}
