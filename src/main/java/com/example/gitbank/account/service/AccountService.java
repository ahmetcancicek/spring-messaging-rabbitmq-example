package com.example.gitbank.account.service;

import com.example.gitbank.account.dto.*;
import com.example.gitbank.account.model.Account;

import java.math.BigDecimal;
import java.util.Optional;

public interface AccountService {
    AccountResponse createAccount(AccountRequest accountRequest);

    AccountResponse updateAccount(String id, AccountRequest accountRequest);

    Optional<Account> findById(String id);

    AccountResponse getAccountById(String id);

    void deleteAccount(String id);

    AccountResponse withdrawMoney(String id, BigDecimal amount);

    AccountResponse withdrawMoney(WithdrawMoneyRequest withdrawMoneyRequest);

    AccountResponse depositMoney(String id, BigDecimal amount);

    AccountResponse depositMoney(DepositMoneyRequest depositMoneyRequest);

    MoneyTransferResponse transferMoney(MoneyTransferRequest moneyTransferRequest);
}
