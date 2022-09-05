package com.example.gitbank.customer.service;

import com.example.gitbank.common.exception.ResourceNotFoundException;
import com.example.gitbank.customer.converter.CustomerConverter;
import com.example.gitbank.customer.dto.CustomerRequest;
import com.example.gitbank.customer.dto.CustomerResponse;
import com.example.gitbank.customer.repository.CustomerRepository;
import com.example.gitbank.customer.model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    CustomerServiceImpl customerService;

    CustomerRepository customerRepository;

    ApplicationEventPublisher applicationEventPublisher;

    CustomerConverter customerConverter;

    @BeforeEach
    void setUp() {
        customerRepository = Mockito.mock(CustomerRepository.class);
        customerConverter = Mockito.mock(CustomerConverter.class);
        applicationEventPublisher = Mockito.mock(ApplicationEventPublisher.class);
        customerService = new CustomerServiceImpl(customerRepository, customerConverter, applicationEventPublisher);
    }

    @Test
    void givenValidRequest_whenSaveCustomer_thenShouldReturnCustomer() {
        // given
        Customer customer = Customer.builder()
                .id(UUID.randomUUID().toString())
                .firstName("George")
                .lastName("Chair")
                .securityNo(UUID.randomUUID().toString())
                .dateOfBirth(LocalDate.of(1980, 11, 12))
                .build();

        CustomerResponse customerResponse = CustomerResponse.builder()
                .id(customer.getId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .securityNo(customer.getSecurityNo())
                .dateOfBirth(customer.getDateOfBirth())
                .build();

        given(customerConverter.toCustomer(any())).willReturn(customer);
        given(customerConverter.fromCustomer(any())).willReturn(customerResponse);
        given(customerRepository.existsBySecurityNo(any())).willReturn(false);
        given(customerRepository.save(any())).willReturn(customer);

        // when
        CustomerRequest customerRequest = CustomerRequest.builder()
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .securityNo(customer.getSecurityNo())
                .dateOfBirth(customer.getDateOfBirth())
                .build();

        CustomerResponse expectedCustomerResponse = customerService.createCustomer(customerRequest);

        // then
        verify(customerRepository, times(1)).save(any());
        assertEquals(customer.getSecurityNo(), expectedCustomerResponse.getSecurityNo());
        assertEquals(customer.getDateOfBirth(), expectedCustomerResponse.getDateOfBirth());
        assertEquals(customer.getFirstName(), expectedCustomerResponse.getFirstName());
        assertEquals(customer.getLastName(), expectedCustomerResponse.getLastName());
        assertEquals(customer.getId(), expectedCustomerResponse.getId());
    }

    @Test
    void givenExistingById_whenFindById_thenReturnCustomer() {
        // given
        Customer customer = Customer.builder()
                .id(UUID.randomUUID().toString())
                .firstName("George")
                .lastName("Chair")
                .securityNo(UUID.randomUUID().toString())
                .dateOfBirth(LocalDate.of(1980, 11, 12))
                .build();

        given(customerRepository.findById(any())).willReturn(Optional.ofNullable(customer));

        // when
        Optional<Customer> expectedCustomer = customerService.findById(customer.getId());

        // then
        verify(customerRepository, times(1)).findById(any());
        assertTrue(expectedCustomer.isPresent());
        assertEquals(customer.getSecurityNo(), expectedCustomer.get().getSecurityNo());
        assertEquals(customer.getDateOfBirth(), expectedCustomer.get().getDateOfBirth());
        assertEquals(customer.getFirstName(), expectedCustomer.get().getFirstName());
        assertEquals(customer.getLastName(), expectedCustomer.get().getLastName());
        assertEquals(customer.getId(), expectedCustomer.get().getId());
    }

    @Test
    void givenExistingBySecurityNo_whenFindBySecurityNo_thenReturnCustomer() {
        // given
        Customer customer = Customer.builder()
                .id(UUID.randomUUID().toString())
                .firstName("George")
                .lastName("Chair")
                .securityNo(UUID.randomUUID().toString())
                .dateOfBirth(LocalDate.of(1980, 11, 12))
                .build();

        given(customerRepository.findBySecurityNo(any())).willReturn(Optional.ofNullable(customer));

        // when
        Optional<Customer> expectedCustomer = customerService.findBySecurityNo(customer.getSecurityNo());

        // then
        verify(customerRepository, times(1)).findBySecurityNo(any());
        assertTrue(expectedCustomer.isPresent());
        assertEquals(customer.getSecurityNo(), expectedCustomer.get().getSecurityNo());
        assertEquals(customer.getDateOfBirth(), expectedCustomer.get().getDateOfBirth());
        assertEquals(customer.getFirstName(), expectedCustomer.get().getFirstName());
        assertEquals(customer.getLastName(), expectedCustomer.get().getLastName());
        assertEquals(customer.getId(), expectedCustomer.get().getId());
    }

    @Test
    void givenExistingById_whenGetCustomerById_thenReturnCustomer() {
        // given
        Customer customer = Customer.builder()
                .id(UUID.randomUUID().toString())
                .firstName("George")
                .lastName("Chair")
                .securityNo(UUID.randomUUID().toString())
                .dateOfBirth(LocalDate.of(1980, 11, 12))
                .build();

        CustomerResponse customerResponse = CustomerResponse.builder()
                .id(customer.getId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .securityNo(customer.getSecurityNo())
                .dateOfBirth(customer.getDateOfBirth())
                .build();

        given(customerConverter.fromCustomer(any())).willReturn(customerResponse);
        given(customerRepository.findById(any())).willReturn(Optional.of(customer));

        // when
        CustomerResponse expectedCustomerResponse = customerService.getCustomerById(customer.getId());

        // then
        verify(customerRepository, times(1)).findById(any());
        assertNotNull(expectedCustomerResponse);
        assertEquals(customer.getSecurityNo(), expectedCustomerResponse.getSecurityNo());
        assertEquals(customer.getDateOfBirth(), expectedCustomerResponse.getDateOfBirth());
        assertEquals(customer.getFirstName(), expectedCustomerResponse.getFirstName());
        assertEquals(customer.getLastName(), expectedCustomerResponse.getLastName());
        assertEquals(customer.getId(), expectedCustomerResponse.getId());
    }

    @Test
    void givenNotExistingById_whenGetCustomerById_thenThrowException() {
        // when
        Throwable throwable = catchThrowable(() -> {
            customerService.getCustomerById(UUID.randomUUID().toString());
        });

        // then
        assertThat(throwable).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void givenExistingCustomer_whenUpdateCustomer_thenShouldReturnCustomer() {
        // given

        // when

        // then
    }

    @Test
    void givenExistingCustomer_whenDeleteCustomer_thenShouldDeleteCustomer() {
        // given

        // when

        // then
    }
}