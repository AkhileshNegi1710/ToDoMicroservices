package com.example.microservices.ProjectMicroservices.dao;

import com.example.microservices.ProjectMicroservices.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDao extends JpaRepository<User, String> {

//    name strategy
    Optional<User> findByEmail(String email);

//    Query annotation
//    @Query(value = "select * from users where email=:email", nativeQuery = true)
//    Optional<User> findUserByQueryEmail(@Param("email") String email);

//native method
//    User findOne(String email);



}
