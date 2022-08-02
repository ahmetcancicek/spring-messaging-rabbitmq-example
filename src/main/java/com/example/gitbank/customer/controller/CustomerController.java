package com.example.gitbank.customer.controller;

import com.example.gitbank.common.dto.ApiResponse;
import com.example.gitbank.customer.dto.CustomerRequest;
import com.example.gitbank.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity createCustomer(@RequestBody CustomerRequest customerRequest) {
        return customerService.createCustomer(customerRequest).map((customerResponse) -> {
            log.info("Customer created successfully [" + customerResponse.toString() + "]");
            return ResponseEntity.ok(new ApiResponse(true, "Customer created successfully"));
        }).orElseThrow(() -> new RuntimeException(""));
    }

}
