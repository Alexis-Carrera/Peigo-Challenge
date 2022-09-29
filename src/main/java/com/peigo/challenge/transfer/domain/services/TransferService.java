package com.peigo.challenge.transfer.domain.services;

import com.peigo.challenge.accounts.application.dto.request.AccountRequest;
import com.peigo.challenge.accounts.application.dto.response.AccountResponse;
import com.peigo.challenge.accounts.application.dto.response.SingleAccountResponse;
import com.peigo.challenge.transfer.application.dto.request.TransferRequest;
import com.peigo.challenge.transfer.application.dto.response.TransferResponse;

public interface TransferService {

    TransferResponse createNewWireTransfer(TransferRequest transferRequest, Long customerId);

}
