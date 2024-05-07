package com.website.ecommerce.controller;

import org.springframework.web.bind.annotation.RestController;

import com.website.ecommerce.model.AuthenticationResponse;
import com.website.ecommerce.model.Customer;
import com.website.ecommerce.model.Role;
import com.website.ecommerce.repository.CustomerRepository;
// import com.website.ecommerce.repository.CustomerRepository;
import com.website.ecommerce.service.AuthenticationService;
import com.website.ecommerce.service.CustomerService;
import com.website.ecommerce.service.JwtService;

import jakarta.servlet.http.HttpSession;

import java.util.List;
// import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class AuthenticationController {

    private final AuthenticationService authService;
    private final JwtService jwtService;
    private final CustomerService cs;

    private final CustomerRepository repository;

    public AuthenticationController(AuthenticationService authService, JwtService jwtService, CustomerService cs,
            CustomerRepository repository, HttpSession httpSession) {
        this.authService = authService;
        this.jwtService = jwtService;
        this.cs = cs;
        this.repository = repository;
        this.httpSession = httpSession;
    }

    @Autowired
    private HttpSession httpSession;

    @GetMapping("/user")
    public String getUserInfo() {
        // Retrieve token from session
        String token = (String) httpSession.getAttribute("jwtToken");
        return token;
    }

    
    @GetMapping("/username")
    public String getUsername() {
        // Retrieve token from session
        String token1 = (String) httpSession.getAttribute("jwtToken");
        if (token1 == null)
            return "NO TOKEN";
        else
            return jwtService.extractUsername(token1);
    }
    
    @GetMapping("/get-email")
    public String getEmail() {
        String token1 = (String) httpSession.getAttribute("jwtToken");
        String username = jwtService.extractUsername(token1);

        Customer customer = cs.getCustomersByName(username).get();

        return customer.getEmail();
    }

    @GetMapping("/logout")
    public void killSession() {
        authService.logout();
    }


    @GetMapping("/get_Data")
    public Optional <Customer> getAllData() {
        String token1 = (String) httpSession.getAttribute("jwtToken");
        String username =  jwtService.extractUsername(token1);
        // Use the repository to fetch data based on the username
        Optional <Customer> customers = cs.getCustomersByName(username);
        return customers;
    }

    @GetMapping("/get_roles")
    public Optional<Customer> getAllDatas(@RequestParam String username) {
    // Use the service method to fetch customer data by username
    Optional<Customer> optionalCustomer = cs.getCustomersByName(username);

    // Check if the customer is present
    if (optionalCustomer.isPresent()) {
        Customer customer = optionalCustomer.get();
        customer.setRole(Role.ADMIN);
        cs.updateRole(customer.getUsername()); 
    }
    return optionalCustomer;
    }

    @GetMapping("/get_Role")
    public String getRole() {
        String token1 = (String) httpSession.getAttribute("jwtToken");
        if (token1 == null || token1.isEmpty()) {
            // Handle the case where the token is null or empty
            return "NO_TOKEN";
        }
        String username = jwtService.extractUsername(token1);
        if (username == null) {
            // Handle the case where the username is null
            return "INVALID_TOKEN";
        }
        
        // Use the repository to fetch data based on the username
        Optional<Customer> customerOptional = cs.getCustomersByName(username);
        
        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            if (customer.getRole() == Role.ADMIN) {
                return "ADMIN";
            } else {
                return "USER";
            }
        } else {
            // Handle the case where the customer is not found
            return "CUSTOMER_NOT_FOUND";
        }
    }


@PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody Customer request) {
        if (isValidEmail(request.getEmail()) && !(repository.existsByEmail(request.getEmail()))) {
            if(isValidName(request.getName())){
                if(isValidPhone(request.getPhone())){
                    if(isValidPassword(request.getPassword())){
                        AuthenticationResponse response = authService.register(request);
                        return ResponseEntity.ok(response);
                    }
                    else {
                        return ResponseEntity.badRequest().body(new AuthenticationResponse(null, "Invalid Password"));
                    }
                }
                else {
                    return ResponseEntity.badRequest().body(new AuthenticationResponse(null, "Invalid Phone")); 
                }
            }
            else {
                return ResponseEntity.badRequest().body(new AuthenticationResponse(null, "Invalid or existing Name"));
            }
        } else {
            return ResponseEntity.badRequest().body(new AuthenticationResponse(null, "Invalid or existing email"));
        }
    }

    public static boolean isValidEmail(String email) {
        String regex = "^[a-zA-Z0-9_+&-]+(?:\\.[a-zA-Z0-9_+&-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean isValidName(String name) {
        List<Customer> customers = cs.getCustomers();
        for (Customer customer : customers) {
            if (customer.getName().equals(name)) {
                return false; // Name exists in the list of customers
            }
        }
        if (name.length() < 4 || name.length() > 20)
            return false;
        else
            return true;
    }

    private boolean isValidPhone(String phone) {
        if(!(phone.length() == 11) || !(phone.charAt(0) == '0')){
            return false;
        }
        else
            return true;
    }

    private boolean isValidPassword(String password) {
        if(password.length() < 8 ){
            return false;
        }
        else
            return true;
    }

    private boolean isExistedName(String name) {
        List<Customer> customers = cs.getCustomers();
        for (Customer customer : customers) {
            if (customer.getName().equals(name)) {
                return true; // Name exists in the list of customers
            }
        }
            return false;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
        @RequestBody Customer request) {
            if(isExistedName(request.getName())){
                    return ResponseEntity.ok(authService.authenticate(request)); 
            }
            else {
                return ResponseEntity.badRequest().body(new AuthenticationResponse(null, "Invalid Name"));
            }
        }

    @GetMapping("/admin")
    public void adminPage() {
    }
    
    @GetMapping("/get-customer-id")
    public Integer getCustomerId() {
        String token1 = (String) httpSession.getAttribute("jwtToken");
        String username = jwtService.extractUsername(token1);
        
        Optional<Customer> customer = cs.getCustomersByName(username);
        return customer.get().getId();
    }
}
