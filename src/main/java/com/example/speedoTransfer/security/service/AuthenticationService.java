package com.example.speedoTransfer.security.service;

import com.example.speedoTransfer.security.model.AuthenticationRequest;
import com.example.speedoTransfer.security.model.AuthenticationResponse;
import com.example.speedoTransfer.auth.RegisterRequest;
import com.example.speedoTransfer.enumeration.AccountCurrency;
import com.example.speedoTransfer.enumeration.AccountType;
import com.example.speedoTransfer.enumeration.Role;
import com.example.speedoTransfer.exception.custom.UserAlreadyExistsException;
import com.example.speedoTransfer.model.Account;
import com.example.speedoTransfer.model.User;
import com.example.speedoTransfer.repository.AccountRepository;
import com.example.speedoTransfer.repository.UserRepository;
import com.example.speedoTransfer.security.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenUtil jwtTokenUtil;

    private final RefreshTokenService refreshTokenService;

    private final AccountRepository accountRepository;

    private final AuthenticationManager authenticationManager;
    public AuthenticationResponse register(RegisterRequest request) {
        if (Boolean.TRUE.equals(this.repository.findByEmail(request.getEmail()))) {
            throw new UserAlreadyExistsException("User with email " + request.getEmail() + " already exists");
        }


        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .birthDate(request.getBirthDate())
                .country(request.getCountry())
                .build();

        Account account = Account.builder()
                .accountNumber(String.valueOf(new SecureRandom().nextInt(1000000000)))
                .accountName(request.getName())
                .currency(AccountCurrency.USD)
                .accountType(AccountType.SAVINGS)
                .balance((double) new SecureRandom().nextInt(1000000))
                .user(user)
                .build();


        user.setAccount(account);

        var jwtToken = jwtTokenUtil.generateAccessToken(user);
        var refreshToken = jwtTokenUtil.generateRefreshToken(user);

        AuthenticationResponse response = AuthenticationResponse.builder()
                .token(jwtToken)
                .refreshToken(refreshToken)
                .build();


        return response;
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request)
    {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtTokenUtil.generateAccessToken(user);
        var refreshToken = jwtTokenUtil.generateRefreshToken(user);
        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }
    public AuthenticationResponse refreshToken(String refreshToken) {
        RefreshTokenService.TokenInfo tokenInfo = refreshTokenService.getTokenInfo(refreshToken);
        if (tokenInfo == null || tokenInfo.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Refresh token is invalid or expired");
        }

        User user = repository.findById(tokenInfo.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String newAccessToken = jwtTokenUtil.generateAccessToken(user);
        String newRefreshToken = jwtTokenUtil.generateRefreshToken(user);
        refreshTokenService.saveToken(newRefreshToken, user.getId());

        return AuthenticationResponse.builder()
                .token(newAccessToken)
                .refreshToken(newRefreshToken)
                .build();
    }

}
