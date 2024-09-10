package com.example.speedoTransfer.controller;

import com.example.speedoTransfer.dto.AccountDTO;
import com.example.speedoTransfer.dto.TransactionDTO;
import com.example.speedoTransfer.exception.custom.ResourceNotFoundException;
import com.example.speedoTransfer.exception.custom.UserDoesNotExistsException;
import com.example.speedoTransfer.exception.response.ErrorDetails;
import com.example.speedoTransfer.service.IAccountService;
import com.example.speedoTransfer.security.util.CurrentUserUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
@Validated
@Tag(name = "Account Controller", description = "Account controller")
public class AccountController {

    private final IAccountService accountService;
    private final CurrentUserUtil currentUserUtil;



    @Operation(summary = "Get Account by Id")
    @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = AccountDTO.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema(implementation = ErrorDetails.class), mediaType = "application/json")})
    @GetMapping("/getAccountById")
    public AccountDTO getAccountById() throws  UserDoesNotExistsException {
        try {
            return this.accountService.getAccountById(currentUserUtil.getCurrentUserId());
        } catch (AccountNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/getAllTransactions")
    public Set<TransactionDTO> getAllTransactions() throws UserDoesNotExistsException, ResourceNotFoundException {
            return this.accountService.getAllTransactions(currentUserUtil.getCurrentUserId());

    }
}
