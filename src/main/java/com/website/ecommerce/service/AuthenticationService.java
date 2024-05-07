package com.website.ecommerce.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.website.ecommerce.model.AuthenticationResponse;
import com.website.ecommerce.model.Customer;
import com.website.ecommerce.repository.CustomerRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class AuthenticationService {

    @Autowired
    private HttpSession httpSession;

    private final CustomerRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    // private final AuthenticationManager authenticationManager;

    public AuthenticationService(CustomerRepository repository, PasswordEncoder passwordEncoder, JwtService jwtService,
                                AuthenticationManager authenticationManager) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        // this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse register(Customer request) {
        // Check if the email already exists        
            Customer user = new Customer();
            user.setEmail(request.getEmail());
            user.setName(request.getName());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setPhone(request.getPhone());
            user.setRole(request.getRole());
        
            user = repository.save(user);
        
            String token = jwtService.generateToken(user);
        
            return new AuthenticationResponse(token);
        
    }

    public AuthenticationResponse authenticate(Customer request) {
        Customer user = repository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        try {
            if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                return new AuthenticationResponse(null, "Invalid Password");            }
        } catch (BadCredentialsException e) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtService.generateToken(user);

        httpSession.setAttribute("jwtToken", token);

        return new AuthenticationResponse(token);
    }

    public void logout() {
        httpSession.invalidate();
    }
}

