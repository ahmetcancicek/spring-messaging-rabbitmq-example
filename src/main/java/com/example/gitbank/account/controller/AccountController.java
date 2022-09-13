package com.example.gitbank.account.controller;

import com.example.gitbank.account.dto.*;
import com.example.gitbank.account.service.AccountServiceImpl;
import com.example.gitbank.common.rest.ApiResponse;
import com.example.gitbank.common.rest.BaseController;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accounts")
public class AccountController extends BaseController {

    private final AccountServiceImpl accountService;

    @PostMapping
    public ApiResponse<AccountResponse> createAccount(@RequestBody AccountRequest accountRequest) {
        AccountResponse accountResponse = accountService.createAccount(accountRequest);
        return respond(accountResponse);
    }

    @GetMapping("/{id}")
    public ApiResponse<AccountResponse> getAccount(@PathVariable String id) {
        AccountResponse accountResponse = accountService.getAccountById(id);
        return respond(accountResponse);
    }

    @PutMapping("/withdraw")
    public ApiResponse<AccountResponse> withdrawMoney(@RequestBody WithdrawMoneyRequest withdrawMoneyRequest) {
        AccountResponse accountResponse = accountService.withdrawMoney(withdrawMoneyRequest);
        return respond(accountResponse);
    }

    @PutMapping("/deposit")
    public ApiResponse<AccountResponse> depositMoney(@RequestBody DepositMoneyRequest depositMoneyRequest) {
        AccountResponse accountResponse = accountService.depositMoney(depositMoneyRequest);
        return respond(accountResponse);
    }

    @PutMapping("/transfer")
    public ApiResponse<MoneyTransferResponse> transferMoney(@RequestBody MoneyTransferRequest moneyTransferRequest) {
        MoneyTransferResponse moneyTransferResponse = accountService.transferMoney(moneyTransferRequest);
        return respond(moneyTransferResponse);
    }
}
