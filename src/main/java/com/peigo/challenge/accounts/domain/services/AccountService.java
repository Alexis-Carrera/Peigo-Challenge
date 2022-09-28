package com.peigo.challenge.accounts.domain.services;

import com.peigo.challenge.accounts.application.dto.request.AccountRequest;
import com.peigo.challenge.accounts.application.dto.response.AccountResponse;

public interface AccountService {

    AccountResponse getAllAccounts(Long customerId);

    AccountResponse createNewAccount(AccountRequest accountRequest);

}
