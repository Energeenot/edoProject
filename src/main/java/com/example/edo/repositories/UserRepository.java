package com.example.edo.repositories;

import com.example.edo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByMail(String mail);
}
