package com.example.speedoTransfer.Repository;

import com.example.speedoTransfer.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

    User findByEmail(String email);
    boolean existsByEmail(String email);

}
