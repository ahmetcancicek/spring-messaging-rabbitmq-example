package com.example.gitbank.account.service;

import com.example.gitbank.account.dto.*;
import com.example.gitbank.account.mapper.AccountConverter;
import com.example.gitbank.account.repository.AccountRepository;
import com.example.gitbank.account.model.Account;
import com.example.gitbank.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private static final HashMap<String, ReentrantLock> locks = new HashMap<>();

    private ReentrantLock getLock(String id) {
        synchronized (locks) {
            ReentrantLock lock = locks.get(id);
            if (lock == null) {
                lock = new ReentrantLock();
                locks.put(id, lock);
            }
            return lock;
        }
    }

    private final AccountRepository accountRepository;
    private final AccountConverter accountConverter;

    private final AccountNotificationService accountNotificationService;

    @Transactional
    @Override
    public AccountResponse createAccount(AccountRequest accountRequest) {
        log.info("Trying to create a new account: [{}]", accountRequest.toString());
        Account account = accountConverter.toAccountFromAccountRequest(accountRequest);
        accountRepository.save(account);
        log.info("Account saved to database: [{}]", account);
        accountNotificationService.sendNotificationForCreatedAccount(account);
        return accountConverter.fromAccountToAccountResponse(account);
    }

    @Override
    public AccountResponse updateAccount(String id, AccountRequest accountRequest) {
        Lock lock = getLock(id);
        try {
            lock.lock();
            log.info("Trying to update account: [{}]", accountRequest.toString());
            return accountRepository.findById(id).map(account -> {
                        account.setBalance(accountRequest.getBalance());
                        account.setCurrency(accountRequest.getCurrency());
                        account.setCustomerId(accountRequest.getCustomerId());
                        accountRepository.save(account);
                        log.info("Account saved to database: [{}]", account);
                        return account;
                    })
                    .map(accountConverter::fromAccountToAccountResponse)
                    .orElseThrow(() -> new ResourceNotFoundException("Account", "id", id));
        } finally {
            lock.unlock();
        }
    }

    @Override
    public AccountResponse getAccountById(String id) {
        return accountRepository.findById(id)
                .map(accountConverter::fromAccountToAccountResponse).orElseThrow(() -> new ResourceNotFoundException("Account", "id", id));
    }

    @Transactional
    @Override
    public void deleteAccount(String id) {
        accountRepository.deleteById(id);
    }

    @Transactional
    @Override
    public AccountResponse withdrawMoney(String id, BigDecimal amount) {
        Lock lock = getLock(id);
        try {
            lock.lock();
            return accountRepository.findById(id).map(account -> {
                        log.info("Trying to withdraw money from account: [{}]", account.toString());
                        if (calculateWithdrawMoney(account.getBalance(), amount)) {
                            account.setBalance(account.getBalance().subtract(amount));
                            accountRepository.save(account);
                            log.info("Withdraw money from account and saved to database: [{}]", account.toString());
                        } else
                            log.info("Insufficient balance -> accountId: [{}] balance: [{}] amount:[{}]", account.getId(), account.getBalance(), amount);
                        return account;
                    }).map(accountConverter::fromAccountToAccountResponse)
                    .orElseThrow(() -> new ResourceNotFoundException("Account", "id", id));
        } finally {
            lock.unlock();
        }
    }

    @Transactional
    public AccountResponse withdrawMoney(WithdrawMoneyRequest withdrawMoneyRequest) {
        return withdrawMoney(withdrawMoneyRequest.getFromId(), withdrawMoneyRequest.getAmount());
    }

    public boolean calculateWithdrawMoney(BigDecimal balance, BigDecimal amount) {
        if (balance.compareTo(amount) >= 0) {
            balance = balance.subtract(amount);
            return true;
        }
        return false;
    }

    @Transactional
    @Override
    public AccountResponse depositMoney(String id, BigDecimal amount) {
        Lock lock = getLock(id);
        try {
            lock.lock();
            return accountRepository.findById(id).map(account -> {
                        log.info("Trying to add money to account: [{}]", account.toString());
                        account.setBalance(account.getBalance().add(amount));
                        account = accountRepository.save(account);
                        log.info("add money to account and saved to database: [{}]", account.toString());
                        return account;
                    }).map(accountConverter::fromAccountToAccountResponse)
                    .orElseThrow(() -> new ResourceNotFoundException("Account", "id", id));
        } finally {
            lock.unlock();
        }
    }

    public AccountResponse depositMoney(DepositMoneyRequest depositMoneyRequest) {
        return depositMoney(depositMoneyRequest.getToId(), depositMoneyRequest.getAmount());
    }

    @Override
    public void transferMoney(MoneyTransferRequest moneyTransferRequest) {
        log.info("Trying to start money transfer operation: [{}]", moneyTransferRequest.toString());
        Lock lock = getLock(moneyTransferRequest.getFromId());
        try {
            accountRepository.findById(moneyTransferRequest.getFromId()).map(fromAccount -> {
                        if (calculateWithdrawMoney(fromAccount.getBalance(), moneyTransferRequest.getAmount())) {
                            fromAccount.getBalance().subtract(moneyTransferRequest.getAmount());
                            accountRepository.findById(moneyTransferRequest.getToId()).map(toAccount -> {
                                toAccount.setBalance(moneyTransferRequest.getAmount());
                                return toAccount;
                            }).orElseThrow(() -> new ResourceNotFoundException("Account", "id", moneyTransferRequest.getToId()));
                        }
                        return fromAccount;
                    })
                    .orElseThrow(() -> new ResourceNotFoundException("Account", "id", moneyTransferRequest.getFromId()));
            lock.lock();
        } finally {
            lock.unlock();
        }
    }
}
