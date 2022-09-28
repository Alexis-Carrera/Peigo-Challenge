package com.peigo.challenge.accounts.application.services;

import com.peigo.challenge.accounts.application.dto.request.AccountRequest;
import com.peigo.challenge.accounts.application.dto.response.AccountList;
import com.peigo.challenge.accounts.application.dto.response.AccountResponse;
import com.peigo.challenge.accounts.application.dto.response.SingleAccountResponse;
import com.peigo.challenge.accounts.domain.entity.AccountEntity;
import com.peigo.challenge.accounts.domain.model.Account;
import com.peigo.challenge.accounts.domain.repositories.AccountRepository;
import com.peigo.challenge.accounts.domain.services.AccountService;
import com.peigo.challenge.customer.application.dto.request.UpdateCustomerRequest;
import com.peigo.challenge.customer.application.dto.response.CustomerResponse;
import com.peigo.challenge.customer.domain.entity.CustomerEntity;
import com.peigo.challenge.customer.domain.model.Customer;
import com.peigo.challenge.customer.domain.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public AccountResponse getAllAccounts(Long customerId) {
        Optional<CustomerEntity> customer = customerRepository.findById(customerId);
        if(customer.isPresent()){
            return createAccountResponse(customerId);
        }
        return AccountResponse.builder()
                .accounts(AccountList.builder().build())
                .httpStatus(HttpStatus.NOT_FOUND)
                .build();
    }

    @Override
    public SingleAccountResponse getAccount(Long accountId) {
        Optional<AccountEntity> account = accountRepository.findById(accountId);
        if(account.isPresent()){
            AccountEntity response = account.get();
            Account result = Account.builder()
                    .accountNumber(response.getAccountNumber())
                    .balance(response.getBalance())
                    .id(response.getId())
                    .build();
            return SingleAccountResponse.builder()
                    .account(result)
                    .httpStatus(HttpStatus.OK)
                    .build();

        }
        return SingleAccountResponse.builder()
                .account(Account.builder().build())
                .httpStatus(HttpStatus.NOT_FOUND)
                .build();
    }

    @Override
    public AccountResponse createNewAccount(AccountRequest accountRequest) {
        Optional<CustomerEntity> customer = customerRepository.findById(accountRequest.getCustomerId());
        if(customer.isPresent()){
            Optional<AccountEntity> accountEntity = accountRepository.findByAccountNumber(accountRequest.getAccountNumber());
            if(accountEntity.isPresent()){
                return AccountResponse.builder()
                        .accounts(AccountList.builder().build())
                        .httpStatus(HttpStatus.CONFLICT)
                        .build();
            }
            AccountEntity account = AccountEntity.builder()
                            .customer(customer.get())
                            .accountNumber(accountRequest.getAccountNumber())
                            .balance(accountRequest.getBalance())
                            .build();
            accountRepository.save(account);
            return createAccountResponse(accountRequest.getCustomerId());
        }
        return AccountResponse.builder()
                .accounts(AccountList.builder().build())
                .httpStatus(HttpStatus.NOT_FOUND)
                .build();
    }


    private AccountResponse createAccountResponse(Long customerId){
        Optional<CustomerEntity> customerEntityOptional = customerRepository.findById(customerId);
        if(customerEntityOptional.isPresent()){
            CustomerEntity customer = customerEntityOptional.get();
            Optional<List<AccountEntity>>  accountEntityList = accountRepository.findAllByCustomer(customer);
            AccountList result = AccountList.builder()
                    .customer(Customer.builder()
                            .nationalIdNumber(customer.getNationalIdNumber())
                            .name(customer.getName())
                            .id(customer.getId())
                            .build())
                    .accounts(accountEntityList.get())
                    .build();
            return AccountResponse.builder()
                    .accounts(result)
                    .httpStatus(HttpStatus.OK)
                    .build();
        }
        return AccountResponse.builder()
                .httpStatus(HttpStatus.NOT_FOUND)
                .build();
    }
}
