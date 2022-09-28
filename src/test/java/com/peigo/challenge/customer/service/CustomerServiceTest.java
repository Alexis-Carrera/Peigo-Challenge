package com.peigo.challenge.customer.service;

import com.peigo.challenge.customer.application.dto.request.CreateCustomerRequest;
import com.peigo.challenge.customer.application.dto.request.UpdateCustomerRequest;
import com.peigo.challenge.customer.application.dto.response.CustomerResponse;
import com.peigo.challenge.customer.application.services.CustomerServiceImpl;
import com.peigo.challenge.customer.domain.entity.CustomerEntity;
import com.peigo.challenge.customer.domain.model.Customer;
import com.peigo.challenge.customer.domain.repositories.CustomerRepository;
import com.peigo.challenge.customer.domain.services.CustomerService;
import com.peigo.challenge.util.TestDataInitializationHelper;
import org.hamcrest.core.IsAnything;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

import static com.peigo.challenge.util.TestDataInitializationHelper.INVALID_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith({MockitoExtension.class, SpringExtension.class})
@ContextConfiguration(initializers = ConfigDataApplicationContextInitializer.class)
public class CustomerServiceTest {

    private final TestDataInitializationHelper helper = new TestDataInitializationHelper();

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Mock
    private CustomerRepository customerRepository;


    @Test
    void shouldReturnGivenValidCustomerIdGetCustomer() {
        CustomerEntity customer = helper.getValidCustomer();

        given(customerRepository.findById(customer.getId()))
                .willReturn(Optional.of(customer));

        assertThat(customerService.getCustomer(customer.getId())).isNotNull().isInstanceOf(
                CustomerResponse.class);
    }

    @Test
    void shouldReturnValidInformationGivenValidCustomerIdGetCustomer() {
        CustomerEntity customer = helper.getValidCustomer();

        given(customerRepository.findById(customer.getId()))
                .willReturn(Optional.of(customer));

        CustomerResponse result = customerService.getCustomer(customer.getId());

        assertThat(result.getCustomer().getId()).isEqualTo(customer.getId());
        assertThat(result.getCustomer().getName()).isEqualTo(customer.getName());
        assertThat(result.getCustomer().getNationalIdNumber()).isEqualTo(customer.getNationalIdNumber());
    }

    @Test
    void shouldReturnNotFoundGivenInvalidCustomerIdGetCustomer() {
        given(customerRepository.findById(INVALID_ID))
                .willReturn(Optional.empty());

        CustomerResponse result = customerService.getCustomer(INVALID_ID);

        assertThat(result.getHttpStatus()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldReturnGivenValidCustomerIdCreateCustomer() {
        CreateCustomerRequest customer = helper.getValidCreateCustomerRequest();

        CustomerEntity customerEntity = helper.getValidCustomer();

        given(customerRepository.findByNationalIdNumber(customer.getNationalIdNumber()))
                .willReturn(Optional.empty());

        given(customerRepository.save(customerEntity))
                .willReturn(customerEntity);

        assertThat(customerService.createNewCustomer(customer)).isNotNull().isInstanceOf(
                CustomerResponse.class);
    }

    @Test
    void shouldReturnHttpFoundGivenCustomerIdThatExistsCreateCustomer() {
        CreateCustomerRequest customer = helper.getValidCreateCustomerRequest();

        CustomerEntity customerEntity = helper.getValidCustomer();

        given(customerRepository.findByNationalIdNumber(customer.getNationalIdNumber()))
                .willReturn(Optional.of(customerEntity));


        assertThat(customerService.createNewCustomer(customer).getHttpStatus()).isEqualTo(HttpStatus.FOUND);

    }

    @Test
    void shouldReturnHttpCreatedGivenInvalidCustomerIdCreateCustomer() {
        CreateCustomerRequest customer = helper.getValidCreateCustomerRequest();

        CustomerEntity customerEntity = helper.getValidCustomer();

        given(customerRepository.findByNationalIdNumber(customer.getNationalIdNumber()))
                .willReturn(Optional.empty());

        given(customerRepository.save(customerEntity))
                .willReturn(customerEntity);

        CustomerResponse result = customerService.createNewCustomer(customer);
        assertThat(result.getHttpStatus()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    void shouldReturnHttpNotFoundGivenInvalidCustomerIdUpdateCustomer() {
        given(customerRepository.findById(INVALID_ID))
                .willReturn(Optional.empty());

        UpdateCustomerRequest customer = helper.getValidUpdateCustomerRequest();

        CustomerResponse result = customerService.updateCustomer(INVALID_ID,customer);

        assertThat(result.getHttpStatus()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldReturnValidInformationGivenValidCustomerIdUpdateCustomer() {
        CustomerEntity customer = helper.getValidCustomer();

        UpdateCustomerRequest request = helper.getValidUpdateCustomerRequest();

        given(customerRepository.findById(customer.getId()))
                .willReturn(Optional.of(customer));
        CustomerEntity anotherCustomer = customer;
        anotherCustomer.setName(request.getName());
        given(customerRepository.save(anotherCustomer))
                .willReturn(anotherCustomer);

        CustomerResponse result = customerService.updateCustomer(customer.getId(),request);

        assertThat(result.getHttpStatus()).isEqualTo(HttpStatus.OK);

        assertThat(result.getCustomer().getName()).isEqualTo(request.getName());
    }
}
