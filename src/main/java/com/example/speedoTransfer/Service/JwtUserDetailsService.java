package com.example.speedoTransfer.Service;

import com.example.speedoTransfer.Model.User;
import com.example.speedoTransfer.Repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if(user == null)
        {
            throw new UsernameNotFoundException("User Not Found " + username);
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail() , user.getPassword() , new ArrayList<>());
    }
}
