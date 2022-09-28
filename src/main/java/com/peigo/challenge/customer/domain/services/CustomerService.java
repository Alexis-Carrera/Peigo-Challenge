package com.peigo.challenge.customer.domain.services;

import com.peigo.challenge.accounts.application.dto.request.AccountRequest;
import com.peigo.challenge.accounts.application.dto.response.AccountResponse;
import com.peigo.challenge.customer.application.dto.request.CreateCustomerRequest;
import com.peigo.challenge.customer.application.dto.request.UpdateCustomerRequest;
import com.peigo.challenge.customer.application.dto.response.CustomerResponse;

public interface CustomerService {

    CustomerResponse getCustomer(Long customerId);

    CustomerResponse createNewCustomer(CreateCustomerRequest accountRequest);

    CustomerResponse updateCustomer(Long customerId, UpdateCustomerRequest accountRequest);

}
