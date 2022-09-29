package com.peigo.challenge.accounts.domain.services;

import com.peigo.challenge.accounts.application.dto.request.AccountRequest;
import com.peigo.challenge.accounts.application.dto.response.AccountResponse;
import com.peigo.challenge.accounts.application.dto.response.SingleAccountResponse;
import com.peigo.challenge.accounts.domain.model.Account;

import java.util.List;

public interface AccountService {

    AccountResponse getAllAccounts(Long customerId);

    SingleAccountResponse getAccount(Long accountId);

    AccountResponse createNewAccount(AccountRequest accountRequest, Long customerId);

}
