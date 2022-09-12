package com.example.gitbank.customer.controller;

import com.example.gitbank.common.rest.ApiResponse;
import com.example.gitbank.common.rest.BaseController;
import com.example.gitbank.customer.dto.CustomerRequest;
import com.example.gitbank.customer.dto.CustomerResponse;
import com.example.gitbank.customer.service.CustomerServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customers")
public class CustomerController extends BaseController {

    private final CustomerServiceImpl customerService;

    @PostMapping
    public ApiResponse<CustomerResponse> createCustomer(@RequestBody CustomerRequest customerRequest) {
        CustomerResponse createdCustomer = customerService.createCustomer(customerRequest);
        return respond(createdCustomer);
    }

    @GetMapping("/{id}")
    public ApiResponse<CustomerResponse> getCustomerById(@PathVariable String id) {
        CustomerResponse customer = customerService.getCustomerById(id);
        return respond(customer);
    }

    @PutMapping("/{id}")
    public ApiResponse<CustomerResponse> updateCustomer(@PathVariable String id,
                                                        @RequestBody CustomerRequest customerRequest) {
        CustomerResponse customer = customerService.updateCustomer(id, customerRequest);
        return respond(customer);
    }

    @DeleteMapping("({id}")
    public ApiResponse<String> deleteCustomer(@PathVariable String id) {
        customerService.deleteCustomer(id);
        return respond("Customer deleted successfully");
    }
}
