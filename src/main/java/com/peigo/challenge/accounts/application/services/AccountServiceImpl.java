package com.peigo.challenge.accounts.application.services;

import com.peigo.challenge.accounts.application.dto.request.AccountRequest;
import com.peigo.challenge.accounts.application.dto.response.AccountResponse;
import com.peigo.challenge.accounts.domain.repositories.AccountRepository;
import com.peigo.challenge.accounts.domain.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public AccountResponse getAllAccounts(Long customerId) {
        return null;
    }

    @Override
    public AccountResponse createNewAccount(AccountRequest accountRequest) {
        return null;
    }

}
