package com.example.speedoTransfer.controller;

import com.example.speedoTransfer.dto.TransactionDTO;
import com.example.speedoTransfer.dto.TransactionWithAccountDTO;
import com.example.speedoTransfer.model.Transaction;
import com.example.speedoTransfer.repository.TransactionRepository;
import com.example.speedoTransfer.service.ITransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final ITransactionService transactionService;

    private final TransactionRepository transactionRepository;

    @PostMapping("/transfer/{userId}")
    public TransactionDTO Transfer(@RequestBody TransactionDTO transactionDTO,Long userId) {
        return transactionService.Transfer(transactionDTO,userId);
    }

    @GetMapping("/{transactionid}")
    public TransactionWithAccountDTO getTransactionById(@PathVariable Long transactionid) {
        return this.transactionService.getTransactionById(transactionid);
    }
    @GetMapping("/getAllTransactions/{userid}")
    public List<TransactionWithAccountDTO> getTransactionByAccountId(@PathVariable Long userid) {
        List<Transaction> transactions = transactionRepository.findBySenderAccountId(userid);
        return transactions.stream()
                .map(Transaction::toDTOWithAccount)
                .collect(Collectors.toList());
    }
}
