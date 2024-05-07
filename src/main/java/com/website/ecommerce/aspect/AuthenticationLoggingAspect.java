package com.website.ecommerce.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.website.ecommerce.model.Customer;

@Component
@Aspect
public class AuthenticationLoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationLoggingAspect.class);

    @Around("execution(public * com.website.ecommerce.service.AuthenticationService.authenticate(..))")
    public Object logAuthentication(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        String username = "Unknown";

        if (args != null && args.length > 0 && args[0] instanceof Customer) {
            Customer customer = (Customer) args[0];
            username = customer.getUsername();
        }

        logger.info("Attempting authentication for user: {}", username);

        try {
            Object result = joinPoint.proceed(); // Proceed with the method invocation
            logger.info("Authentication for user '{}' successful.", username);
            return result;
        } catch (RuntimeException e) {
            logger.warn("Authentication for user '{}' failed.", username);
            throw e; // Re-throw the exception
        }
    }

    @Around("execution(public * com.website.ecommerce.service.AuthenticationService.register(..))")
    public Object logRegistration(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        String email = "Unknown";

        if (args != null && args.length > 0 && args[0] instanceof Customer) {
            Customer customer = (Customer) args[0];
            email = customer.getName();
        }

        logger.info("Attempting registration for username: {}", email);

        try {
            Object result = joinPoint.proceed(); // Proceed with the method invocation
            logger.info("Registration for username '{}' successful.", email);
            return result;
        } catch (RuntimeException e) {
            logger.warn("Registration for username '{}' failed.", email);
            throw e; // Re-throw the exception
        }
    }
}
