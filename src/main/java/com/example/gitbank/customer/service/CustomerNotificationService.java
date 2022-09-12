package com.example.gitbank.customer.service;

import com.example.gitbank.customer.model.Customer;

public interface CustomerNotificationService {
    void sendToQueue(Customer customer);
}
