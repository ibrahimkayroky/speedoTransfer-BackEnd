package com.example.speedoTransfer.controller;

import com.example.speedoTransfer.dto.TransactionTransferDTO;
import com.example.speedoTransfer.exception.custom.ResourceNotFoundException;
import com.example.speedoTransfer.model.User;
import com.example.speedoTransfer.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import com.example.speedoTransfer.service.ITransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final ITransactionService transactionService;

    private final UserRepository userRepository;

    @PostMapping("/transfer")
    public TransactionTransferDTO Transfer(@RequestBody TransactionTransferDTO transactionTransferDTO) throws ResourceNotFoundException {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
        return transactionService.Transfer(transactionTransferDTO,user.getId());
    }


}
