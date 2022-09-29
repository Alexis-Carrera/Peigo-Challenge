package com.peigo.challenge.accounts.infrastructure.controller;

import com.auth0.jwt.JWT;
import com.peigo.challenge.accounts.application.dto.request.AccountRequest;
import com.peigo.challenge.accounts.application.dto.response.AccountResponse;
import com.peigo.challenge.accounts.application.dto.response.SingleAccountResponse;
import com.peigo.challenge.accounts.domain.model.Account;
import com.peigo.challenge.accounts.domain.services.AccountService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Consumes;
import java.util.List;

import static com.peigo.challenge.util.Constant.USER_ID;

@Log4j2
@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/account/create")
    @Consumes("application/json")
    public ResponseEntity accountCreate(@RequestBody AccountRequest accountRequest,
                                        @RequestHeader(name = "token") String token) {
        String id = JWT.decode(token).getClaim(USER_ID).asString();
        Long customerId = Long.valueOf(id);
        AccountResponse accountInformation = accountService.createNewAccount(accountRequest,customerId);
        return new ResponseEntity(accountInformation.getAccounts(), accountInformation.getHttpStatus());
    }

    @GetMapping("/accounts")
    @Consumes("application/json")
    public ResponseEntity getAccounts(@RequestHeader(name = "token") String token) {
        String id = JWT.decode(token).getClaim(USER_ID).asString();
        Long customerId = Long.valueOf(id);
        AccountResponse accountInformationList = accountService.getAllAccounts(customerId);
        return new ResponseEntity(accountInformationList.getAccounts(), accountInformationList.getHttpStatus());
    }


    @GetMapping("/account/{accountId}")
    @Consumes("application/json")
    public ResponseEntity getAccouns(@PathVariable Long accountId,
                                     @RequestHeader(name = "token") String token) {
        String id = JWT.decode(token).getClaim(USER_ID).asString();
        SingleAccountResponse accountInformation = accountService.getAccount(accountId);
        return new ResponseEntity(accountInformation.getAccount(), accountInformation.getHttpStatus());
    }
}