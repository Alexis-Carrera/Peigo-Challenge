package com.peigo.challenge.customer.infrastructure.controller;

import com.auth0.jwt.JWT;
import com.peigo.challenge.customer.application.dto.request.CreateCustomerRequest;
import com.peigo.challenge.customer.application.dto.request.UpdateCustomerRequest;
import com.peigo.challenge.customer.application.dto.response.CustomerResponse;
import com.peigo.challenge.customer.domain.services.CustomerService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Consumes;

import static com.peigo.challenge.util.Constant.USER_ID;

@Log4j2
@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/customer/create")
    @Consumes("application/json")
    public ResponseEntity customerCreate(@RequestBody CreateCustomerRequest customerRequest,
        @RequestHeader(name = "token") String token) {
        String customerId = JWT.decode(token).getClaim(USER_ID).asString();
        CustomerResponse customerResponse = customerService.createNewCustomer(customerRequest);
        return new ResponseEntity(customerResponse.getCustomer(), customerResponse.getHttpStatus());
    }

    @GetMapping("/customer")
    @Consumes("application/json")
    public ResponseEntity getCustomer(@RequestHeader(name = "token") String token) {
        String id = JWT.decode(token).getClaim(USER_ID).asString();
        Long customerId = Long.valueOf(id);
        CustomerResponse customerResponse = customerService.getCustomer(customerId);
        return new ResponseEntity(customerResponse.getCustomer(), customerResponse.getHttpStatus());
    }

    @PatchMapping("/customer")
    @Consumes("application/json")
    public ResponseEntity updateCustomer(@RequestBody UpdateCustomerRequest customerRequest,
                                         @RequestHeader(name = "token") String token) {
        String id = JWT.decode(token).getClaim(USER_ID).asString();
        Long customerId = Long.valueOf(id);
        CustomerResponse customerResponse = customerService.updateCustomer(customerId, customerRequest);
        return new ResponseEntity(customerResponse, HttpStatus.OK);
    }
}