package com.example.speedoTransfer.service;

import com.example.speedoTransfer.dto.UpdateUserDTO;
import com.example.speedoTransfer.dto.UserDTO;
import com.example.speedoTransfer.exception.custom.FavoriteRecipientException;
import com.example.speedoTransfer.exception.custom.ResourceNotFoundException;
import com.example.speedoTransfer.model.Account;
import com.example.speedoTransfer.model.FavoriteRecipient;
import com.example.speedoTransfer.model.User;
import com.example.speedoTransfer.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUserById_Success() throws ResourceNotFoundException {

        Long userId = 1L;
        User user = new User();
        user.setId(userId);

        Account account = new Account();
        account.setBalance(1000.0);
        user.setAccount(account);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        UserDTO result = userService.getUserById(userId);

        assertNotNull(result);
        assertEquals(1000.0, result.getBalance());
        verify(userRepository,times(1)).findById(userId);
    }

    @Test
    void testGetUserById_UserNotFound() {

        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.getUserById(userId));
        verify(userRepository,times(1)).findById(userId);
    }

    @Test
    void testUpdateUser_Success() {

        String email = "ibrahim@gmail.com";
        UpdateUserDTO updateUserDTO = new UpdateUserDTO();
        updateUserDTO.setName("ibrahim");
        updateUserDTO.setEmail("kayroky@gmail.com");

        Account account = new Account();
        account.setBalance(1000.0);

        User user = new User();
        user.setEmail(email);
        user.setAccount(account);


        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDTO result = userService.updateUser(email, updateUserDTO);

        assertNotNull(result);
        assertEquals("kayroky@gmail.com", user.getEmail());
        verify(userRepository,times(1)).findByEmail(email);
    }

    @Test
    void testUpdateUser_UserNotFound() {

        String email = "ibrahim@gmail.com";
        UpdateUserDTO updateUserDTO = new UpdateUserDTO();
        updateUserDTO.setName("ibrahim");
        updateUserDTO.setEmail("kayroky@gmail.com");

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            userService.updateUser(email, updateUserDTO);
        });
        verify(userRepository,times(1)).findByEmail(email);

    }

    @Test
    void testUpdatePassword_Success() {

        String email = "ibrahim@gmail.com";
        String oldPassword = "oldPassword";
        String newPassword = "newPassword";

        User user = new User();
        user.setEmail(email);
        user.setPassword(oldPassword);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(oldPassword, user.getPassword())).thenReturn(true);
        when(passwordEncoder.encode(newPassword)).thenReturn("encodedPassword");

        userService.updatePassword(email, oldPassword, newPassword);
        assertEquals("encodedPassword", user.getPassword());
        verify(userRepository,times(1)).findByEmail(email);

    }

    @Test
    void testUpdatePassword_InvalidOldPassword() {
        String email = "ibrahim@example.com";
        String oldPassword = "oldPass";
        String newPassword = "newPass";

        User user = new User();
        user.setEmail(email);
        user.setPassword("encodedOldPass");

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(oldPassword, user.getPassword())).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> userService.updatePassword(email, oldPassword, newPassword));
        verify(userRepository, never()).save(user);
    }

    @Test
    void testGetAllFavoriteRecipients_Success() throws FavoriteRecipientException {
        Long userId = 1L;
        Set<FavoriteRecipient> favoriteRecipients = new HashSet<>();
        favoriteRecipients.add(new FavoriteRecipient());

        User user = new User();
        user.setFavoriteRecipients(favoriteRecipients);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        Set<FavoriteRecipient> result = userService.getAllFavoriteRecipients(userId);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void testGetAllFavoriteRecipients_UserNotFound() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(FavoriteRecipientException.class, () -> userService.getAllFavoriteRecipients(userId));
        verify(userRepository, times(1)).findById(userId);
    }
}
