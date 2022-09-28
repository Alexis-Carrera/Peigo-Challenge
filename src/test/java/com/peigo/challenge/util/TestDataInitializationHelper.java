package com.peigo.challenge.util;

import com.peigo.challenge.customer.application.dto.request.CreateCustomerRequest;
import com.peigo.challenge.customer.application.dto.request.UpdateCustomerRequest;
import com.peigo.challenge.customer.domain.entity.CustomerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;


@Service
@Transactional
public class TestDataInitializationHelper {

    public static final Long ID = 1L;

    public static final String VALID_IDENTIFICATION = "1720168291";
    public static final String VALID_NAME = "ALEXIS CARRERA";
    public static final String VALID_NAME_2 = "ALEXIS CARRERA2";

    public static final Long INVALID_ID = 55L;

    @Autowired
    private EntityManager em;
    private TestEntityManager testEntityManager;
    private boolean isInit = false;

    public TestDataInitializationHelper() {
    }

    public TestDataInitializationHelper(TestEntityManager testEntityManager) {
        this.testEntityManager = testEntityManager;
    }

    public void initOffersTestDataSet( String schema ) {
        if (isInit) {
            return;
        }

        //create a correct registry
        persist(getValidCustomer());

        clear();
        isInit = true;
    }

    private void persist(Object target) {
        if (testEntityManager != null) {
            testEntityManager.persistAndFlush(target);
        } else if (em != null) {
            em.persist(target);
            em.flush();
        }

    }

    public CustomerEntity getValidCustomer() {
        return CustomerEntity.builder()
                .name(VALID_NAME)
                .nationalIdNumber(VALID_IDENTIFICATION)
                .build();
    }

    public CreateCustomerRequest getValidCreateCustomerRequest() {
        return CreateCustomerRequest.builder()
                .name(VALID_NAME)
                .nationalIdNumber(VALID_IDENTIFICATION)
                .build();
    }

    public UpdateCustomerRequest getValidUpdateCustomerRequest() {
        return UpdateCustomerRequest.builder()
                .name(VALID_NAME_2)
                .build();
    }


    private void clear() {
        if (testEntityManager != null) {
            testEntityManager.clear();
        } else if (em != null) {
            em.clear();
        }
    }

}