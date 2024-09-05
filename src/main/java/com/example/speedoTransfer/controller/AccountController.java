package com.example.speedoTransfer.controller;

import com.example.speedoTransfer.dto.AccountDTO;
import com.example.speedoTransfer.dto.CreateAccountDTO;
import com.example.speedoTransfer.exception.custom.ResourceNotFoundException;
import com.example.speedoTransfer.exception.response.ErrorDetails;
import com.example.speedoTransfer.service.IAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
@Validated
@Tag(name = "Account Controller", description = "Account controller")
public class AccountController {

    private final IAccountService accountService;

    @Operation(summary = "Create new Account")
    @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = AccountDTO.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema(implementation = ErrorDetails.class), mediaType = "application/json")})
    @PostMapping
    public AccountDTO createAccount(@Valid @RequestBody CreateAccountDTO accountDTO) throws ResourceNotFoundException {
        return this.accountService.createAccount(accountDTO);
    }

    @Operation(summary = "Get Account by Id")
    @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = AccountDTO.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema(implementation = ErrorDetails.class), mediaType = "application/json")})
    @GetMapping("/{accountId}")
    public AccountDTO getAccountById(@PathVariable Long accountId) throws ResourceNotFoundException {
        return this.accountService.getAccountById(accountId);
    }
}
