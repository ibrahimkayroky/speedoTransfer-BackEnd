package com.example.speedoTransfer.controller;

import com.example.speedoTransfer.dto.UpdatePasswordRequest;
import com.example.speedoTransfer.dto.UpdateUserDTO;
import com.example.speedoTransfer.dto.UserDTO;
import com.example.speedoTransfer.exception.custom.ResourceNotFoundException;
import com.example.speedoTransfer.model.User;
import com.example.speedoTransfer.repository.UserRepository;
import com.example.speedoTransfer.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    @PutMapping("/updateUserProfile")
    public ResponseEntity<String> updateUser(@RequestBody UpdateUserDTO updateUserDTO){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        userService.updateUser(email, updateUserDTO);
        return ResponseEntity.ok("User updated successfully");

    }

    @PutMapping("/updateUserPassword")
    public ResponseEntity<String> updatePassword(@RequestBody UpdatePasswordRequest password) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        userService.updatePassword(email, password.getOldPassword(), password.getNewPassword());
        return ResponseEntity.ok("Password updated successfully");
    }

    @GetMapping("/getUser")
    public ResponseEntity<UserDTO> getUser() throws ResourceNotFoundException {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
        UserDTO userDTO = user.toDTO();
        return ResponseEntity.ok().body(userDTO);
    }

//    @GetMapping("/getRecipient")
//    public Set<FavoriteRecipient> getRecipient(@RequestParam Long userId) throws FavoriteRecipientException {
//        return userService.getAllFavoriteRecipients(userId);
//    }
}
