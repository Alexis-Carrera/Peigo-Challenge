package com.peigo.challenge.transfer.infrastructure.controller;

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

@Log4j2
@RestController
public class TransferController {

    @Autowired
    private TransferService transferService;

    @PostMapping("/transfer/create")
    @Consumes("application/json")
    public ResponseEntity transferCreate(@RequestBody TransferRequest transferRequest) {
        //@RequestHeader(name = "token") String token) {
        //String userId = JWT.decode(token).getClaim(Constant.USER_ID).asString();
        TransferResponse transferResponse = transferService.createNewWireTransfer(transferRequest);
        return new ResponseEntity(transferResponse.getTransfer(), transferResponse.getHttpStatus());
    }

}