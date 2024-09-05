package com.example.speedoTransfer.auth;

import com.example.speedoTransfer.enumeration.Country;
import com.example.speedoTransfer.enumeration.Role;
import com.example.speedoTransfer.model.User;
import com.example.speedoTransfer.repository.UserRepository;
import com.example.speedoTransfer.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenUtil jwtTokenUtil;

    private final AuthenticationManager authenticationManager;
    public AuthenticationResponse register(RegisterRequest request)
    {
        var user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .birthDate(request.getBirthDate())
                .country(String.valueOf(Country.EGYPT))
                .build();
        repository.save(user);

        var jwtToken = jwtTokenUtil.generateToken(user);
        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .build();
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
