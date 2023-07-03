package com.example.microservices.ProjectMicroservices.dao;

import com.example.microservices.ProjectMicroservices.entities.ToDo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface ToDoDao extends JpaRepository<ToDo, Integer> {
//    to find ToDoClass using ID using name Strategy method
    List<ToDo> findByFkUser(String email);
}
