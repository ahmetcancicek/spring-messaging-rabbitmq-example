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

    CustomerNotificationServiceImpl customerNotificationService;

    CustomerConverter customerConverter;

    @BeforeEach
    void setUp() {
        customerRepository = Mockito.mock(CustomerRepository.class);
        customerConverter = new CustomerConverter();
        customerNotificationService = Mockito.mock(CustomerNotificationServiceImpl.class);
        customerService = new CustomerServiceImpl(customerRepository, customerConverter, customerNotificationService);
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

        CustomerRequest customerRequest = CustomerRequest.builder()
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .securityNo(customer.getSecurityNo())
                .dateOfBirth(customer.getDateOfBirth())
                .build();

        given(customerRepository.existsBySecurityNo(any(String.class))).willReturn(false);
        given(customerRepository.save(any(Customer.class))).willReturn(customer);

        // when
        CustomerResponse expectedCustomerResponse = customerService.createCustomer(customerRequest);

        // then
        verify(customerRepository, times(1)).save(any(Customer.class));
        assertEquals(customer.getId(), expectedCustomerResponse.getId());
        assertEquals(customer.getSecurityNo(), expectedCustomerResponse.getSecurityNo());
        assertEquals(customer.getFirstName(), expectedCustomerResponse.getFirstName());
        assertEquals(customer.getLastName(), expectedCustomerResponse.getLastName());
        assertEquals(customer.getDateOfBirth(), expectedCustomerResponse.getDateOfBirth());
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

        given(customerRepository.findById(any(String.class))).willReturn(Optional.ofNullable(customer));

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

        given(customerRepository.findBySecurityNo(any(String.class))).willReturn(Optional.ofNullable(customer));

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


        given(customerRepository.findById(any(String.class))).willReturn(Optional.of(customer));

        // when
        CustomerResponse expectedCustomerResponse = customerService.getCustomerById(customer.getId());

        // then
        verify(customerRepository, times(1)).findById(any(String.class));
        assertEquals(customer.getId(), expectedCustomerResponse.getId());
        assertEquals(customer.getSecurityNo(), expectedCustomerResponse.getSecurityNo());
        assertEquals(customer.getFirstName(), expectedCustomerResponse.getFirstName());
        assertEquals(customer.getLastName(), expectedCustomerResponse.getLastName());
        assertEquals(customer.getDateOfBirth(), expectedCustomerResponse.getDateOfBirth());

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
        Customer customer = Customer.builder()
                .id(UUID.randomUUID().toString())
                .firstName("George")
                .lastName("Chair")
                .securityNo(UUID.randomUUID().toString())
                .dateOfBirth(LocalDate.of(1980, 11, 12))
                .build();

        CustomerRequest customerRequest = CustomerRequest.builder()
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .securityNo(customer.getSecurityNo())
                .dateOfBirth(customer.getDateOfBirth())
                .build();

        given(customerRepository.findById(any(String.class))).willReturn(Optional.of(customer));
        given(customerRepository.save(any(Customer.class))).willReturn(customer);

        // when
        CustomerResponse expectedCustomerResponse = customerService.updateCustomer(customer.getId(), customerRequest);

        // then
        verify(customerRepository, times(1)).findById(any(String.class));
        verify(customerRepository, times(1)).save(any(Customer.class));
        assertEquals(customer.getId(), expectedCustomerResponse.getId());
        assertEquals(customer.getSecurityNo(), expectedCustomerResponse.getSecurityNo());
        assertEquals(customer.getFirstName(), expectedCustomerResponse.getFirstName());
        assertEquals(customer.getLastName(), expectedCustomerResponse.getLastName());
        assertEquals(customer.getDateOfBirth(), expectedCustomerResponse.getDateOfBirth());
    }

    @Test
    void givenExistingCustomer_whenDeleteCustomer_thenShouldDeleteCustomer() {
        // given

        // when

        // then
    }
}