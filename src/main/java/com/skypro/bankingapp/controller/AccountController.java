package com.skypro.bankingapp.controller;

import com.skypro.bankingapp.dto.AccountDTO;
import com.skypro.bankingapp.dto.request.BalanceChangeRequest;
import com.skypro.bankingapp.dto.request.TransferRequest;
import com.skypro.bankingapp.service.AccountService;
import com.skypro.bankingapp.service.Operation;
import com.skypro.bankingapp.service.TransferService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class AccountController {
    private final AccountService accountService;
    private final TransferService transferService;

    public AccountController(AccountService accountService, TransferService transferService) {
        this.accountService = accountService;
        this.transferService = transferService;
    }

    @GetMapping("/")
    public AccountDTO getAccount(@RequestParam("username") String username,
                                 @RequestParam("account") String accountNumber) {
        return accountService.getAccount(username, accountNumber);
    }

    @PostMapping("/transfer")
    public ResponseEntity<?> transferMoney(@RequestBody TransferRequest transferRequest) {
        transferService.transferMoney(transferRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/deposit")
    public ResponseEntity<?> depositMoney (@RequestBody BalanceChangeRequest balanceChangeRequest) {
        accountService.changeBalance(
                balanceChangeRequest.username(),
                balanceChangeRequest.account(),
                Operation.DEPOSIT,
                balanceChangeRequest.amount());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/withdraw")
    public ResponseEntity<?> withdrawMoney(@RequestBody BalanceChangeRequest balanceChangeRequest) {
        accountService.changeBalance(
                balanceChangeRequest.username(),
                balanceChangeRequest.account(),
                Operation.WITHDRAW,
                balanceChangeRequest.amount());
        return ResponseEntity.ok().build();
    }
}
