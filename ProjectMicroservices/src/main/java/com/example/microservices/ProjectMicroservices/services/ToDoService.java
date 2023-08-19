package com.example.microservices.ProjectMicroservices.services;

import com.example.microservices.ProjectMicroservices.entities.ToDo;

import java.util.List;

public interface ToDoService {

//    below method will return list of user details using email id
    List<ToDo> getToDo(String email);

//    below method will return anything as it is void
    ToDo addToDo(ToDo toDo);

//    below method will not return anything as it is void
    void deleteToDo(String email);


}
