package com.example.bikego.repository;

import com.example.bikego.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    User findByEmail(String email);

    User findByPassword(String password);
}
