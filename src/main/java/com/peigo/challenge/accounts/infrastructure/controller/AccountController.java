package com.peigo.challenge.accounts.infrastructure.controller;

import com.peigo.challenge.accounts.application.dto.request.AccountRequest;
import com.peigo.challenge.accounts.application.dto.response.AccountResponse;
import com.peigo.challenge.accounts.domain.services.AccountService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Consumes;

@Log4j2
@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/account/create")
    @Consumes("application/json")
    public ResponseEntity accountCreate(@RequestBody AccountRequest accountRequest) {
        //@RequestHeader(name = "token") String token) {
        //String userId = JWT.decode(token).getClaim(Constant.USER_ID).asString();
        AccountResponse accountResponse = accountService.createNewAccount(accountRequest);
        return new ResponseEntity(accountResponse, HttpStatus.OK);
    }

    @GetMapping("/accounts/{customerId}")
    @Consumes("application/json")
    public ResponseEntity getAccounts(@PathVariable Long customerId) {
        //@RequestHeader(name = "token") String token) {
        //String userId = JWT.decode(token).getClaim(Constant.USER_ID).asString();
        AccountResponse accountResponse = accountService.getAllAccounts(customerId);
        return new ResponseEntity(accountResponse, HttpStatus.OK);
    }
}