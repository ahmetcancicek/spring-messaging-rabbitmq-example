package com.example.gitbank.account.mapper;

import com.example.gitbank.account.dto.AccountRequest;
import com.example.gitbank.account.dto.AccountResponse;
import com.example.gitbank.account.model.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountConverter {

    public Account toAccount(AccountRequest accountRequest) {
        return Account.builder()
                .customerId(accountRequest.getCustomerId())
                .name(accountRequest.getAccountName())
                .balance(accountRequest.getBalance())
                .currency(accountRequest.getCurrency())
                .build();
    }

    public AccountResponse fromAccount(Account account) {
        return AccountResponse.builder()
                .id(account.getId())
                .name(account.getName())
                .customerId(account.getCustomerId())
                .balance(account.getBalance())
                .currency(account.getCurrency())
                .build();
    }
}
