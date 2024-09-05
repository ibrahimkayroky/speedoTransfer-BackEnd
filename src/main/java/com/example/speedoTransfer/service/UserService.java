package com.example.speedoTransfer.service;

import com.example.speedoTransfer.dto.UpdateUserDTO;
import com.example.speedoTransfer.dto.UserDTO;
import com.example.speedoTransfer.exception.custom.ResourceNotFoundException;
import com.example.speedoTransfer.model.User;
import com.example.speedoTransfer.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDTO getUserById(Long userId) throws ResourceNotFoundException {
        return this.userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"))
                .toDTO();

    }

    @Override
    public UserDTO updateUser(Long userId, UpdateUserDTO updatedUserDTO) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        updateIfPresent(updatedUserDTO.getName(), existingUser::setName);
        updateIfPresent(updatedUserDTO.getEmail(), existingUser::setEmail);
        updateIfPresent(updatedUserDTO.getCountry(), existingUser::setCountry);
        updateIfPresent(updatedUserDTO.getBirthDate(), existingUser::setBirthDate);

        User updatedUser = userRepository.save(existingUser);

        // Convert the updated entity to a DTO and return
        return updatedUser.toDTO();
    }

    @Override
    public void changePassword(Long userId, String oldPassword, String newPassword) {

        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Verify the old password
        if (!passwordEncoder.matches(oldPassword, existingUser.getPassword())) {
            throw new IllegalArgumentException("Old password is incorrect");
        }

        String encodedNewPassword = passwordEncoder.encode(newPassword);
        existingUser.setPassword(encodedNewPassword);

        userRepository.save(existingUser);
    }

    private <T> void updateIfPresent(T value, Consumer<T> setter) {
        if (value != null) {
            setter.accept(value);
        }
    }

}
