package com.example.gitbank.account.mapper;

import com.example.gitbank.account.dto.AccountRequest;
import com.example.gitbank.account.dto.AccountResponse;
import com.example.gitbank.account.messaging.AccountNotification;
import com.example.gitbank.account.model.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountConverter {

    public Account toAccountFromAccountRequest(AccountRequest accountRequest) {
        return Account.builder()
                .customerId(accountRequest.getCustomerId())
                .name(accountRequest.getName())
                .balance(accountRequest.getBalance())
                .currency(accountRequest.getCurrency())
                .build();
    }

    public AccountResponse fromAccountToAccountResponse(Account account) {
        return AccountResponse.builder()
                .id(account.getId())
                .name(account.getName())
                .customerId(account.getCustomerId())
                .balance(account.getBalance())
                .currency(account.getCurrency())
                .build();
    }

    public AccountNotification fromAccountToAccountNotification(Account account) {
        return AccountNotification.builder()
                .id(account.getId())
                .name(account.getName())
                .customerId(account.getCustomerId())
                .balance(account.getBalance())
                .currency(account.getCurrency())
                .build();
    }
}
