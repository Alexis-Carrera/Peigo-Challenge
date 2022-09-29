package com.peigo.challenge.accounts.service;

import com.peigo.challenge.accounts.application.dto.request.AccountRequest;
import com.peigo.challenge.accounts.application.dto.response.AccountResponse;
import com.peigo.challenge.accounts.application.dto.response.SingleAccountResponse;
import com.peigo.challenge.accounts.application.services.AccountServiceImpl;
import com.peigo.challenge.accounts.domain.entity.AccountEntity;
import com.peigo.challenge.accounts.domain.repositories.AccountRepository;
import com.peigo.challenge.customer.application.dto.request.CreateCustomerRequest;
import com.peigo.challenge.customer.application.dto.request.UpdateCustomerRequest;
import com.peigo.challenge.customer.application.dto.response.CustomerResponse;
import com.peigo.challenge.customer.application.services.CustomerServiceImpl;
import com.peigo.challenge.customer.domain.entity.CustomerEntity;
import com.peigo.challenge.customer.domain.repositories.CustomerRepository;
import com.peigo.challenge.util.TestDataInitializationHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static com.peigo.challenge.util.TestDataInitializationHelper.INVALID_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;


@ExtendWith({MockitoExtension.class, SpringExtension.class})
@ContextConfiguration(initializers = ConfigDataApplicationContextInitializer.class)
public class AccountServiceTest {

    private final TestDataInitializationHelper helper = new TestDataInitializationHelper();

    @InjectMocks
    private AccountServiceImpl accountService;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Test
    void shouldReturnGivenValidCustomerIdGetAccount() {
        CustomerEntity customer = helper.getValidCustomer();
        AccountEntity account = helper.getValidAccount();

        given(accountRepository.findById(customer.getId()))
                .willReturn(Optional.of(account));

        assertThat(accountService.getAccount(customer.getId())).isNotNull().isInstanceOf(
                SingleAccountResponse.class);
    }

    @Test
    void shouldReturnNotFoundGivenInvalidCustomerIdGetAccount() {

        given(accountRepository.findById(INVALID_ID))
                .willReturn(Optional.empty());

        SingleAccountResponse result = accountService.getAccount(INVALID_ID);

        assertThat(result.getHttpStatus()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
