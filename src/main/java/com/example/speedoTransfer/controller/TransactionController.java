package com.example.speedoTransfer.controller;

import com.example.speedoTransfer.dto.TransactionDTO;
import com.example.speedoTransfer.model.Transaction;
import com.example.speedoTransfer.service.ITransactionService;
import com.example.speedoTransfer.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final ITransactionService transactionService;

    @PostMapping("/createTransaction")
    public TransactionDTO createTransaction(@RequestBody TransactionDTO transactionDTO) {
        return transactionService.createTransaction(transactionDTO);
    }

    @GetMapping("/{transactionid}")
    public Transaction getTransactionById(@PathVariable Long transactionid) {
        return this.transactionService.getTransactionById(transactionid);
    }
}
