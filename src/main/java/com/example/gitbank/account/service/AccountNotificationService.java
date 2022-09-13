package com.example.gitbank.account.service;

import com.example.gitbank.account.model.Account;

public interface AccountNotificationService {
    void sendNotificationForCreatedAccount(Account Account);
}
