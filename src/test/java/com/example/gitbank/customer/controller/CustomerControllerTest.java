package com.example.gitbank.customer.controller;

import com.example.gitbank.customer.dto.CustomerRequest;
import com.example.gitbank.customer.dto.CustomerResponse;
import com.example.gitbank.customer.service.CustomerServiceImpl;
import com.example.gitbank.customer.model.Customer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
@AutoConfigureMockMvc
class CustomerControllerTest {

    private final static String CUSTOMER_ENDPOINT = "/api/v1/customers";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private CustomerServiceImpl customerService;

    @MockBean
    private ApplicationEventPublisher applicationEventPublisher;

    @Test
    void whenNotExistingCustomer_whenCreateCustomer_thenReturnCustomer() throws Exception {
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

        given(customerService.createCustomer(any())).willReturn(customerResponse);

        // when
        CustomerRequest customerRequest = CustomerRequest.builder()
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .securityNo(customer.getSecurityNo())
                .dateOfBirth(customer.getDateOfBirth())
                .build();

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post(CUSTOMER_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(customerRequest));

        // then
        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(customerResponse.getId()))
                .andExpect(jsonPath("$.data.firstName").value(customerResponse.getFirstName()))
                .andExpect(jsonPath("$.data.lastName").value(customerResponse.getLastName()))
                .andExpect(jsonPath("$.data.securityNo").value(customerResponse.getSecurityNo()))
                .andExpect(jsonPath("$.data.dateOfBirth").value(customerResponse.getDateOfBirth().toString()));
    }

    @Test
    void whenExistingCustomer_whenGetCustomer_thenReturnCustomer() throws Exception {
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

        given(customerService.getCustomerById(any())).willReturn(customerResponse);

        // when
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get(CUSTOMER_ENDPOINT + "/{id}", customer.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        // then
        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(customerResponse.getId()))
                .andExpect(jsonPath("$.data.firstName").value(customerResponse.getFirstName()))
                .andExpect(jsonPath("$.data.lastName").value(customerResponse.getLastName()))
                .andExpect(jsonPath("$.data.securityNo").value(customerResponse.getSecurityNo()))
                .andExpect(jsonPath("$.data.dateOfBirth").value(customerResponse.getDateOfBirth().toString()));
    }

    @Test
    void whenExistingCustomer_whenUpdateCustomer_thenReturnCustomer() {

    }

    @Test
    void whenExistingCustomer_whenDeleteCustomer_thenReturnMessage() {

    }
}