package com.peigo.challenge.customer.infrastructure.controller;

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

@Log4j2
@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/customer/create")
    @Consumes("application/json")
    public ResponseEntity customerCreate(@RequestBody CreateCustomerRequest customerRequest) {
        //@RequestHeader(name = "token") String token) {
        //String userId = JWT.decode(token).getClaim(Constant.USER_ID).asString();
        CustomerResponse customerResponse = customerService.createNewCustomer(customerRequest);
        return new ResponseEntity(customerResponse.getCustomer(), customerResponse.getHttpStatus());
    }

    @GetMapping("/customer/{customerId}")
    @Consumes("application/json")
    public ResponseEntity getCustomer(@PathVariable Long customerId) {
        //@RequestHeader(name = "token") String token) {
        //String userId = JWT.decode(token).getClaim(Constant.USER_ID).asString();
        CustomerResponse customerResponse = customerService.getCustomer(customerId);
        return new ResponseEntity(customerResponse.getCustomer(), customerResponse.getHttpStatus());
    }

    @PatchMapping("/customer/{customerId}")
    @Consumes("application/json")
    public ResponseEntity updateCustomer(@PathVariable Long customerId, @RequestBody UpdateCustomerRequest customerRequest) {
        //@RequestHeader(name = "token") String token) {
        //String userId = JWT.decode(token).getClaim(Constant.USER_ID).asString();
        CustomerResponse customerResponse = customerService.updateCustomer(customerId, customerRequest);
        return new ResponseEntity(customerResponse, HttpStatus.OK);
    }
}