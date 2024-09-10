package com.example.speedoTransfer.security.util;

import com.example.speedoTransfer.exception.custom.UserDoesNotExistsException;
import com.example.speedoTransfer.model.User;
import com.example.speedoTransfer.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CurrentUserUtil {

    private final UserRepository userRepository;

    public User getCurrentUser() throws UserDoesNotExistsException {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserDoesNotExistsException("User not found with email: " + email));
    }

    public Long getCurrentUserId() throws UserDoesNotExistsException {
        return getCurrentUser().getId();
    }
}
