package com.example.speedoTransfer.controller;

import com.example.speedoTransfer.dto.UpdatePasswordRequest;
import com.example.speedoTransfer.dto.UpdateUserDTO;
import com.example.speedoTransfer.dto.UserDTO;
import com.example.speedoTransfer.exception.custom.FavoriteRecipientException;
import com.example.speedoTransfer.exception.custom.ResourceNotFoundException;
import com.example.speedoTransfer.model.FavoriteRecipient;
import com.example.speedoTransfer.model.User;
import com.example.speedoTransfer.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PutMapping("/updateUser/{userId}")
    public UserDTO updateUser(@PathVariable Long userId, @RequestBody UpdateUserDTO updateUserDTO){
        return userService.updateUser(userId, updateUserDTO);
    }

    @PutMapping("/updateUser/{userId}/password")
    public ResponseEntity<String> updatePassword(@PathVariable Long userId, @RequestBody UpdatePasswordRequest password) {
        userService.updatePassword(userId, password.getOldPassword(), password.getNewPassword());
        return ResponseEntity.ok("Password updated successfully");
    }

    @GetMapping("/getUser/{userId}")
    public UserDTO getUser(@PathVariable Long userId) throws ResourceNotFoundException {
        return userService.getUserById(userId);
    }

    @GetMapping("/getRecipient/")
    public Set<FavoriteRecipient> getRecipient(@RequestParam Long userId) throws FavoriteRecipientException {
        return userService.getAllFavoriteRecipients(userId);
    }
}
