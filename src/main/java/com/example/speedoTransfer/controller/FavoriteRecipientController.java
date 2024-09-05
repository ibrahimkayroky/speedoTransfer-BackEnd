package com.example.speedoTransfer.controller;

import com.example.speedoTransfer.dto.FavoriteAccountDTO;
import com.example.speedoTransfer.dto.FavoriteRecipientDTO;
import com.example.speedoTransfer.exception.custom.ResourceNotFoundException;
import com.example.speedoTransfer.model.FavoriteRecipient;
import com.example.speedoTransfer.service.FavoriteRecipientService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
@RequiredArgsConstructor
public class FavoriteRecipientController {

    private final FavoriteRecipientService favoriteRecipientService;


    @PostMapping
    public FavoriteRecipientDTO addFavoriteRecipient(@RequestBody FavoriteRecipientDTO favoriteRecipientDTO) {
        return this.favoriteRecipientService.addFavoriteRecipient(favoriteRecipientDTO);

    }

    @GetMapping("/{id}")
    public FavoriteRecipientDTO getFavoriteRecipientById(@PathVariable Long id) throws ResourceNotFoundException {
        return this.favoriteRecipientService.getFavoriteRecipientById(id);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<FavoriteRecipient>> getFavoriteRecipientsByAccountId(@PathVariable Integer accountId) {
        List<FavoriteRecipient> favoriteRecipients = favoriteRecipientService.getFavoriteRecipientsByAccountId(accountId);
        return ResponseEntity.ok(favoriteRecipients);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFavoriteRecipientById(@PathVariable Long id) {
        favoriteRecipientService.deleteFavoriteRecipientById(id);
        return ResponseEntity.noContent().build();
    }
}
