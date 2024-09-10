package com.example.speedoTransfer.service;

import com.example.speedoTransfer.dto.FavoriteRecipientDTO;
import com.example.speedoTransfer.exception.custom.FavoriteRecipientNotFoundException;
import com.example.speedoTransfer.exception.custom.UserDoesNotExistsException;
import com.example.speedoTransfer.model.FavoriteRecipient;
import com.example.speedoTransfer.model.User;
import com.example.speedoTransfer.repository.FavoriteRecipientRepository;
import com.example.speedoTransfer.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class FavoriteRecipientServiceTest {

    @Mock
    private FavoriteRecipientRepository favoriteRecipientRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private FavoriteRecipientService favoriteRecipientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddFavoriteRecipient_Success() throws UserDoesNotExistsException {
        // Setup
        String email = "user@example.com";
        User user = new User();
        user.setId(1L);
        user.setEmail(email);

        FavoriteRecipientDTO favoriteRecipientDTO = new FavoriteRecipientDTO();
        favoriteRecipientDTO.setRecipientName("John Doe");
        favoriteRecipientDTO.setRecipientAccount("123456789");

        FavoriteRecipient favoriteRecipient = new FavoriteRecipient();
        favoriteRecipient.setRecipientName(favoriteRecipientDTO.getRecipientName());
        favoriteRecipient.setRecipientAccount(favoriteRecipientDTO.getRecipientAccount());
        favoriteRecipient.setUser(user);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(favoriteRecipientRepository.save(any(FavoriteRecipient.class))).thenReturn(favoriteRecipient);

        // Mock Security Context
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn(email);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // Execute
        FavoriteRecipientDTO result = favoriteRecipientService.addFavoriteRecipient(favoriteRecipientDTO);

        // Verify
        assertNotNull(result);
        assertEquals(favoriteRecipientDTO.getRecipientName(), result.getRecipientName());
        assertEquals(favoriteRecipientDTO.getRecipientAccount(), result.getRecipientAccount());
        verify(userRepository, times(1)).findByEmail(email);
        verify(favoriteRecipientRepository, times(1)).save(any(FavoriteRecipient.class));
    }



    @Test
    void testGetFavoriteRecipientById_NotFound() {
        // Setup
        Long id = 1L;
        when(favoriteRecipientRepository.findById(id)).thenReturn(Optional.empty());

        FavoriteRecipientNotFoundException thrown = assertThrows(FavoriteRecipientNotFoundException.class, () -> {
            favoriteRecipientService.getFavoriteRecipientById(id);
        });
        assertEquals("FavoriteRecipient not found with id: " + id, thrown.getMessage());
        verify(favoriteRecipientRepository, times(1)).findById(id);
    }

@Test
void testDeleteFavoriteRecipientById_Success() {
    // Setup
    Long id = 1L;
    when(favoriteRecipientRepository.existsById(id)).thenReturn(true);

    // Execute
    favoriteRecipientService.deleteFavoriteRecipientById(id);

    // Verify
    verify(favoriteRecipientRepository, times(1)).deleteById(id);
}

@Test
void testDeleteFavoriteRecipientById_NotFound() {
    // Setup
    Long id = 1L;
    when(favoriteRecipientRepository.existsById(id)).thenReturn(false);

    // Execute & Verify
    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
        favoriteRecipientService.deleteFavoriteRecipientById(id);
    });
    assertEquals("FavoriteRecipient not found", thrown.getMessage());
    verify(favoriteRecipientRepository, never()).deleteById(id);
}

@Test
void testDeleteFavoriteRecipientByUserIdAndDetails_Success() throws FavoriteRecipientNotFoundException {
    // Setup
    Long userId = 1L;
    String recipientAccount = "123456789";

    FavoriteRecipient favoriteRecipient = new FavoriteRecipient();
    favoriteRecipient.setId(1L);
    favoriteRecipient.setRecipientAccount(recipientAccount);

    when(favoriteRecipientRepository.findByUserIdAndRecipientAccount(userId, recipientAccount))
            .thenReturn(Optional.of(favoriteRecipient));

    // Execute
    favoriteRecipientService.deleteFavoriteRecipientByUserIdAndDetails(userId, recipientAccount);

    // Verify
    verify(favoriteRecipientRepository, times(1)).delete(favoriteRecipient);
}

@Test
void testDeleteFavoriteRecipientByUserIdAndDetails_NotFound() {
    // Setup
    Long userId = 1L;
    String recipientAccount = "123456789";

    when(favoriteRecipientRepository.findByUserIdAndRecipientAccount(userId, recipientAccount))
            .thenReturn(Optional.empty());

    // Execute & Verify
    FavoriteRecipientNotFoundException thrown = assertThrows(FavoriteRecipientNotFoundException.class, () -> {
        favoriteRecipientService.deleteFavoriteRecipientByUserIdAndDetails(userId, recipientAccount);
    });
    assertEquals("Favorite recipient not found for user with ID: " + userId, thrown.getMessage());
    verify(favoriteRecipientRepository, never()).delete(any(FavoriteRecipient.class));
}
}
