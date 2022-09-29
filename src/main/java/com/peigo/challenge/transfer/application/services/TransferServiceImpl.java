package com.peigo.challenge.transfer.application.services;

import com.peigo.challenge.accounts.application.dto.request.AccountRequest;
import com.peigo.challenge.accounts.application.dto.response.AccountList;
import com.peigo.challenge.accounts.application.dto.response.AccountResponse;
import com.peigo.challenge.accounts.application.dto.response.SingleAccountResponse;
import com.peigo.challenge.accounts.domain.entity.AccountEntity;
import com.peigo.challenge.accounts.domain.model.Account;
import com.peigo.challenge.accounts.domain.repositories.AccountRepository;
import com.peigo.challenge.accounts.domain.services.AccountService;
import com.peigo.challenge.customer.domain.entity.CustomerEntity;
import com.peigo.challenge.customer.domain.model.Customer;
import com.peigo.challenge.customer.domain.repositories.CustomerRepository;
import com.peigo.challenge.transfer.application.dto.request.TransferRequest;
import com.peigo.challenge.transfer.application.dto.response.TransferResponse;
import com.peigo.challenge.transfer.domain.entity.TransferEntity;
import com.peigo.challenge.transfer.domain.model.Transfer;
import com.peigo.challenge.transfer.domain.repositories.TransferRepository;
import com.peigo.challenge.transfer.domain.services.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransferServiceImpl implements TransferService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransferRepository transferRepository;

    @Override
    public TransferResponse createNewWireTransfer(TransferRequest transferRequest) {

        Optional<AccountEntity> rootAccountNumberOptional = accountRepository.findByAccountNumber(transferRequest.getRootAccountNumber());

        if(rootAccountNumberOptional.isEmpty()){
            return TransferResponse.builder().httpStatus(HttpStatus.NOT_FOUND).build();
        }

        Optional<AccountEntity> destinationAccountNumberOptional = accountRepository.findByAccountNumber(transferRequest.getDestinationAccountNumber());

        if(destinationAccountNumberOptional.isEmpty()){
            return TransferResponse.builder().httpStatus(HttpStatus.NOT_FOUND).build();
        }

        AccountEntity rootAccountNumber = rootAccountNumberOptional.get();
        AccountEntity destinationAccountNumber = destinationAccountNumberOptional.get();

        BigDecimal valueRootBalance = rootAccountNumber.getBalance();
        BigDecimal valueDestinationBalance = destinationAccountNumber.getBalance();

        if(valueRootBalance.compareTo(transferRequest.getAmount())<0)
        {
            return TransferResponse.builder().httpStatus(HttpStatus.CONFLICT).build();
        }

        rootAccountNumber.setBalance(valueRootBalance.subtract(transferRequest.getAmount()));
        destinationAccountNumber.setBalance(valueDestinationBalance.add(transferRequest.getAmount()));

        AccountEntity rootAccountEntity = accountRepository.save(rootAccountNumber);
        AccountEntity destinationAccountEntity = accountRepository.save(destinationAccountNumber);
        TransferEntity transferResult =TransferEntity.builder()
                .balance(transferRequest.getAmount())
                .destinationAccountNumber(transferRequest.getDestinationAccountNumber())
                .rootAccountNumber(transferRequest.getRootAccountNumber())
                .build();

        TransferEntity transferResultEntity = transferRepository.save(transferResult);

        Account rootAccount = Account.builder()
                .accountNumber(rootAccountEntity.getAccountNumber())
                .id(rootAccountEntity.getId())
                .balance(rootAccountEntity.getBalance())
                .build();

        Account destinationAccount = Account.builder()
                .accountNumber(destinationAccountEntity.getAccountNumber())
                .id(destinationAccountEntity.getId())
                .balance(destinationAccountEntity.getBalance())
                .build();

        return TransferResponse.builder()
                .transfer(Transfer.builder()
                        .transferNumber(transferResultEntity.getId())
                        .destinationAccount(destinationAccount)
                        .rootAccount(rootAccount)
                        .build()
                )
                .httpStatus(HttpStatus.OK)
                .build();
    }
}
