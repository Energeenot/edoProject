package com.example.edo.repositories;

import com.example.edo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface UserRepository extends JpaRepository<User, Long> {
    User findByMail(String mail);
    User findByNumberGroup(String numberGroup);

    @Query("SELECT u FROM User u WHERE u.numberGroup = :numberGroup")
    List<User> findAllByNumberGroup(@Param("numberGroup") String numberGroup);

    @Query("SELECT DISTINCT u.numberGroup FROM User u WHERE u.numberGroup IS NOT NULL")
    List<String> findAllNumberGroup();
}