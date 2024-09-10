package com.example.speedoTransfer.controller;

import com.example.speedoTransfer.dto.FavoriteRecipientDTO;
import com.example.speedoTransfer.exception.custom.FavoriteRecipientNotFoundException;
import com.example.speedoTransfer.exception.custom.UserDoesNotExistsException;
import com.example.speedoTransfer.model.FavoriteRecipient;
import com.example.speedoTransfer.service.FavoriteRecipientService;
import com.example.speedoTransfer.security.util.CurrentUserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
@RequiredArgsConstructor
public class FavoriteRecipientController {

    private final FavoriteRecipientService favoriteRecipientService;
    private final CurrentUserUtil currentUserUtil;


    @PostMapping
    public FavoriteRecipientDTO addFavoriteRecipient(@RequestBody FavoriteRecipientDTO favoriteRecipientDTO) throws UserDoesNotExistsException {
        return this.favoriteRecipientService.addFavoriteRecipient(favoriteRecipientDTO);

    }


    @GetMapping("/userFavorites")
    public ResponseEntity<List<FavoriteRecipient>> getFavoriteRecipientsByUserId() throws UserDoesNotExistsException {
        List<FavoriteRecipient> favoriteRecipients = favoriteRecipientService.getFavoriteRecipientsByUserId(currentUserUtil.getCurrentUserId());
        return ResponseEntity.ok(favoriteRecipients);
    }

    @DeleteMapping("/deleteFavoriteRecipient")
    public ResponseEntity<Void> deleteFavoriteRecipient(

            @RequestParam String recipientAccount) throws UserDoesNotExistsException, FavoriteRecipientNotFoundException
    {
        Long userId = currentUserUtil.getCurrentUserId();
        favoriteRecipientService.deleteFavoriteRecipientByUserIdAndDetails(userId, recipientAccount);
        return ResponseEntity.noContent().build();
    }
}
