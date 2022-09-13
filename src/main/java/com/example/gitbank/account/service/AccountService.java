package com.example.gitbank.account.service;

import com.example.gitbank.account.dto.*;

import java.math.BigDecimal;

public interface AccountService {
    AccountResponse createAccount(AccountRequest accountRequest);

    AccountResponse updateAccount(String id, AccountRequest accountRequest);

    AccountResponse getAccountById(String id);

    void deleteAccount(String id);

    AccountResponse withdrawMoney(String id, BigDecimal amount);

    AccountResponse withdrawMoney(WithdrawMoneyRequest withdrawMoneyRequest);

    AccountResponse depositMoney(String id, BigDecimal amount);

    AccountResponse depositMoney(DepositMoneyRequest depositMoneyRequest);

    void transferMoney(MoneyTransferRequest moneyTransferRequest);
}
