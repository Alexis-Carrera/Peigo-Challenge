package com.peigo.challenge.transfer.infrastructure.controller;

import com.auth0.jwt.JWT;
import com.peigo.challenge.accounts.application.dto.request.AccountRequest;
import com.peigo.challenge.accounts.application.dto.response.AccountResponse;
import com.peigo.challenge.accounts.application.dto.response.SingleAccountResponse;
import com.peigo.challenge.accounts.domain.services.AccountService;
import com.peigo.challenge.transfer.application.dto.request.TransferRequest;
import com.peigo.challenge.transfer.application.dto.response.TransferResponse;
import com.peigo.challenge.transfer.domain.services.TransferService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Consumes;

import static com.peigo.challenge.util.Constant.USER_ID;

@Log4j2
@RestController
public class TransferController {

    @Autowired
    private TransferService transferService;

    @PostMapping("/transfer/create")
    @Consumes("application/json")
    public ResponseEntity transferCreate(@RequestBody TransferRequest transferRequest,
                                         @RequestHeader(name = "token") String token) {
        String id = JWT.decode(token).getClaim(USER_ID).asString();
        Long customerId = Long.valueOf(id);
        TransferResponse transferResponse = transferService.createNewWireTransfer(transferRequest, customerId);
        return new ResponseEntity(transferResponse.getTransfer(), transferResponse.getHttpStatus());
    }

}