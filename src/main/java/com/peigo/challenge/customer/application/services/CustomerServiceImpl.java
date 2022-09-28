package com.peigo.challenge.customer.application.services;

import com.peigo.challenge.customer.application.dto.request.CreateCustomerRequest;
import com.peigo.challenge.customer.application.dto.request.UpdateCustomerRequest;
import com.peigo.challenge.customer.application.dto.response.CustomerResponse;
import com.peigo.challenge.customer.domain.entity.CustomerEntity;
import com.peigo.challenge.customer.domain.model.Customer;
import com.peigo.challenge.customer.domain.repositories.CustomerRepository;
import com.peigo.challenge.customer.domain.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public CustomerResponse getCustomer(Long customerId) {
        Optional<CustomerEntity> customer = customerRepository.findById(customerId);
        if(customer.isPresent()){
            return createCustomerResponse(customer.get(), HttpStatus.OK);
        }
        return CustomerResponse.builder()
                .customer(Customer.builder().build())
                .httpStatus(HttpStatus.NOT_FOUND)
                .build();
    }

    @Override
    public CustomerResponse createNewCustomer(CreateCustomerRequest accountRequest) {
        //Look if customer already exist
        Optional<CustomerEntity> customer = customerRepository.findByNationalIdNumber(accountRequest.getNationalIdNumber());
        if(customer.isPresent()){
            return createCustomerResponse(customer.get(), HttpStatus.FOUND);
        }
        CustomerEntity customerEntity = CustomerEntity.builder()
                    .name(accountRequest.getName())
                    .nationalIdNumber(accountRequest.getNationalIdNumber())
                    .build();
        CustomerEntity result = customerRepository.save(customerEntity);
        return createCustomerResponse(result, HttpStatus.CREATED);
    }

    @Override
    public CustomerResponse updateCustomer(Long customerId, UpdateCustomerRequest accountRequest) {
        //Look if customer already exist
        Optional<CustomerEntity> customer = customerRepository.findById(customerId);
        if(customer.isPresent()){
            CustomerEntity customerEntity = customer.get();
            customerEntity.setName(accountRequest.getName());
            CustomerEntity result = customerRepository.save(customerEntity);
            return createCustomerResponse(result, HttpStatus.OK);
        }
        return CustomerResponse.builder()
                .customer(Customer.builder().build())
                .httpStatus(HttpStatus.NOT_FOUND)
                .build();
    }


    private CustomerResponse createCustomerResponse(CustomerEntity customerEntity, HttpStatus httpStatus){
        return CustomerResponse.builder()
                .customer(Customer.builder()
                        .id(customerEntity.getId())
                        .name(customerEntity.getName())
                        .nationalIdNumber(customerEntity.getNationalIdNumber())
                        .build())
                .httpStatus(httpStatus)
                .build();
    }
}
