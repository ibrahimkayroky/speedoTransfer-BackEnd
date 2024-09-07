package com.example.speedoTransfer.auth;

import com.example.speedoTransfer.enumeration.AccountCurrency;
import com.example.speedoTransfer.enumeration.AccountType;
import com.example.speedoTransfer.enumeration.Country;
import com.example.speedoTransfer.enumeration.Role;
import com.example.speedoTransfer.exception.custom.UserAlreadyExistsException;
import com.example.speedoTransfer.model.Account;
import com.example.speedoTransfer.model.User;
import com.example.speedoTransfer.repository.AccountRepository;
import com.example.speedoTransfer.repository.UserRepository;
import com.example.speedoTransfer.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenUtil jwtTokenUtil;

    private final AccountRepository accountRepository;

    private final AuthenticationManager authenticationManager;
    public RegisterResponse register(RegisterRequest request)
    {
        if (Boolean.TRUE.equals(this.repository.findByEmail(request.getEmail()))) {
            throw new UserAlreadyExistsException("User with email " + request.getEmail() + " already exists");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .birthDate(request.getBirthDate())
                .country(String.valueOf(Country.EGYPT))
                .build();
//        repository.save(user);




        Account account = Account.builder()
                .accountNumber(new SecureRandom().nextInt(1000000000) + "")
                .accountName(request.getName())
                .currency(AccountCurrency.USD)
                .accountType(AccountType.SAVINGS)
                .balance((double) new SecureRandom().nextInt(1000000))
                .user(user)
                .build();

//
//        var jwtToken = jwtTokenUtil.generateToken(user);
//        return AuthenticationResponse
//                .builder()
//                .token(jwtToken)
//                .build();
//
        user.setAccount(account);

//        user.getAccounts()
        User savedUser = repository.save(user);


//
//        accountRepository.save(account);
//

        return savedUser.toRegistrationResponse();
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
        var jwtToken = jwtTokenUtil.generateToken(user);
        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .build();
    }
}
