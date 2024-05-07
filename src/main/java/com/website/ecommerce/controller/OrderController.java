package com.website.ecommerce.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.website.ecommerce.dto.OrderAnItemDTO;
import com.website.ecommerce.dto.OrderDTO;
import com.website.ecommerce.model.Customer;
import com.website.ecommerce.model.Order;
import com.website.ecommerce.model.OrderItem;
import com.website.ecommerce.model.Role;
// import com.website.ecommerce.service.AuthenticationService;
import com.website.ecommerce.service.CustomerService;
import com.website.ecommerce.service.JwtService;
import com.website.ecommerce.service.OrderService;

import jakarta.servlet.http.HttpSession;

@RestController
public class OrderController {

    private final JwtService jwtService;
    private final CustomerService cs;

    public OrderController(JwtService jwtService, CustomerService cs,
            OrderService orderService, HttpSession httpSession) {
        this.jwtService = jwtService;
        this.cs = cs;
        this.orderService = orderService;
        this.httpSession = httpSession;
    }

    @Autowired
    private HttpSession httpSession;

    @Autowired
    private OrderService orderService;

    @GetMapping("/get-recent-orders")
    public List<OrderDTO> getRecentOrders() {
        List<Order> orders = orderService.getRecentOrders();
        List<OrderDTO> orderDTOs = new ArrayList<>();

        String token1 = (String) httpSession.getAttribute("jwtToken");
        String username = jwtService.extractUsername(token1);

        // Use the repository to fetch data based on the username
        Optional<Customer> customerOptional = cs.getCustomersByName(username);

        Customer customer = customerOptional.get();
        if (customer.getRole() == Role.ADMIN) {
            for (Order order : orders) {
                OrderDTO orderDTO = new OrderDTO();
                orderDTO.setId(order.getId());
                orderDTO.setCustomerPhone(order.getCustomer().getPhone());
                orderDTO.setOrderDate(order.getOrderDate());
                orderDTO.setTotalAmount(order.getTotalAmount());
                orderDTO.setStatus(order.getStatus());
                orderDTO.setAddress(order.getAddress());
                orderDTOs.add(orderDTO);
            }
        }
        return orderDTOs;
    }
        
    @PostMapping("/place-order")
    public String saveOrder(@RequestBody OrderDTO orderDTO) {
        Integer customer_id = orderDTO.getCustomerId();
        if(isValidId(customer_id))
        {
            orderService.placeOrder(orderDTO);
            return "ORDER PLACED SUCCESFULLY";
        }
        else
            return "ERROR NO SUCH CUSTOMER ID";
    }

    private boolean isValidId(Integer customer_id) {
        List<Customer> customers = cs.getCustomers();
        for (Customer customer : customers) {
            if (customer.getId()  == customer_id) {
                return true; // Name exists in the list of customers
            }
        }
                return false;
    }

    @GetMapping("/get-all-orders/{customerId}")
    public List<OrderDTO> getAllOrdersByCustomerId(@PathVariable Long customerId) {
    List<Order> orders = orderService.getAllOrdersByCustomerId(customerId);
    List<OrderDTO> orderDTOs = new ArrayList<>();

    for (Order order : orders) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setCustomerPhone(order.getCustomer().getPhone());
        orderDTO.setOrderDate(order.getOrderDate());
        orderDTO.setTotalAmount(order.getTotalAmount());
        orderDTO.setStatus(order.getStatus());
        orderDTO.setAddress(order.getAddress());

        List<OrderItem> orderItems = order.getOrderItems();
        List<OrderAnItemDTO> orderItemDTOs = new ArrayList<>();

        for (OrderItem orderItem : orderItems) {
            OrderAnItemDTO orderItemDTO = new OrderAnItemDTO();
            orderItemDTO.setQuantity(orderItem.getQuantity());
            orderItemDTO.setPrice(orderItem.getProduct().getPrice());
            orderItemDTO.setImageBase64(orderItem.getProduct().getImageBase64());
            orderItemDTOs.add(orderItemDTO);
        }

        orderDTO.setOrderItemss(orderItemDTOs);
        orderDTOs.add(orderDTO);
    }

    return orderDTOs;
}

    @GetMapping("/count-orders")
    public Long countOrders() {
        String token1 = (String) httpSession.getAttribute("jwtToken");
        if(token1 != null)
        {
            String username = jwtService.extractUsername(token1);
        
        // Use the repository to fetch data based on the username
        Optional<Customer> customerOptional = cs.getCustomersByName(username);
        
            Customer customer1 = customerOptional.get();
            if (customer1.getRole() == Role.ADMIN)
            {
                Long ordersCount = orderService.countOrders();
                return ordersCount;
            }
            else
                return 0L;
        }
        else
                return 0L;
    }

    @GetMapping("/count-earnings")
    public Long countEarnings() {
        String token1 = (String) httpSession.getAttribute("jwtToken");
        if (token1 != null) {
            String username = jwtService.extractUsername(token1);

            // Use the repository to fetch data based on the username
            Optional<Customer> customerOptional = cs.getCustomersByName(username);

            Customer customer1 = customerOptional.get();
            if (customer1.getRole() == Role.ADMIN) {
                Long countEarnings = orderService.countEarnings();
                return countEarnings;
            } else
                return 0L;
        } else
            return 0L;
    }
    
    @GetMapping("/get-all-orders")
    public List<OrderDTO> getAllRecentOrders() {
        List<Order> orders = orderService.getAllRecentOrders();
        List<OrderDTO> orderDTOs = new ArrayList<>();

        String token1 = (String) httpSession.getAttribute("jwtToken");
        String username = jwtService.extractUsername(token1);

        // Use the repository to fetch data based on the username
        Optional<Customer> customerOptional = cs.getCustomersByName(username);

        Customer customer = customerOptional.get();
        if (customer.getRole() == Role.ADMIN) {
            for (Order order : orders) {
                OrderDTO orderDTO = new OrderDTO();
                orderDTO.setId(order.getId());
                orderDTO.setCustomerPhone(order.getCustomer().getPhone());
                orderDTO.setOrderDate(order.getOrderDate());
                orderDTO.setTotalAmount(order.getTotalAmount());
                orderDTO.setStatus(order.getStatus());
                orderDTO.setAddress(order.getAddress());
                orderDTOs.add(orderDTO);
            }
        }
        return orderDTOs;
    }
    
    @PostMapping("/update-order/{orderId}")
    public ResponseEntity<OrderDTO> updateOrderStatus(@PathVariable Integer orderId, @RequestBody Order request) {
        // Check if the authenticated user is an admin
        // String username = request.getUsername(); // Assuming the username is passed in the request
        // Optional<Customer> customerOptional = cs.getCustomersByName(username);

                // Update the order status
                Optional<Order> orderOptional = orderService.getOrderById(orderId);
                if (orderOptional.isPresent()) {
                    Order order = orderOptional.get();
                    order.setStatus(request.getStatus());
                    orderService.saveOrder(order);
                    // Construct and return the OrderDTO
                    OrderDTO orderDTO = new OrderDTO();
                    orderDTO.setId(order.getId());
                    orderDTO.setCustomerPhone(order.getCustomer().getPhone());
                    orderDTO.setOrderDate(order.getOrderDate());
                    orderDTO.setTotalAmount(order.getTotalAmount());
                    orderDTO.setStatus(order.getStatus());
                    orderDTO.setAddress(order.getAddress());
                    return ResponseEntity.ok(orderDTO);
                } else {
                    return ResponseEntity.notFound().build();
                }
            }
}
