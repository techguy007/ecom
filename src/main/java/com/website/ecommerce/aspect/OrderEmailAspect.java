package com.website.ecommerce.aspect;

import com.website.ecommerce.dto.OrderDTO;
import com.website.ecommerce.model.Order;
import com.website.ecommerce.service.EmailSenderService;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.logging.Level;
import java.util.logging.Logger;

@Aspect
@Component
public class OrderEmailAspect {

    private static final Logger LOGGER = Logger.getLogger(OrderEmailAspect.class.getName());

    @Autowired
    private EmailSenderService emailSenderService;

    private String toEmail;
    private String subject;
    private String body;

    @Pointcut("execution(* com.website.ecommerce.service.OrderService.placeOrder(..)) && args(orderDTO)")
    public void placeOrderPointcut(OrderDTO orderDTO) {}

    @AfterReturning(pointcut = "placeOrderPointcut(orderDTO)", returning = "order")
    public void sendOrderEmail(OrderDTO orderDTO, Order order) {
        if (order != null) {
            try {
                toEmail = orderDTO.getCustomerEmail();
                subject = "Order Confirmation";
                body = "Dear " + orderDTO.getCustomerName() + ",\n\nThank you for your order. Your order details are:\n\n" +
                        "Order ID: " + order.getId() + "\n" +
                        "Total Amount: " + order.getTotalAmount() + "\n" +
                        "Order Date: " + order.getOrderDate() + "\n\n" +
                        "Best regards,\n" +
                        "Your Company   ";
                sendEmail();
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Error sending order confirmation email: " + e.getMessage(), e);
            }
        }
    }

    private void sendEmail() {
        emailSenderService.sendEmail(toEmail, subject, body);
        LOGGER.info("Order confirmation email sent successfully to: " + toEmail);
    }
}
