package com.example.microservices.ProjectMicroservices.services;

import com.example.microservices.ProjectMicroservices.dao.ToDoDao;
import com.example.microservices.ProjectMicroservices.entities.ToDo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ToDoServiceImpl implements ToDoService{

    @Autowired
    ToDoDao toDoDao;

    @Override
    public List<ToDo> getToDo(String email) {
        return toDoDao.findByFkUser(email);
    }

    @Override
    public ToDo addToDo(ToDo toDo) {
        return toDoDao.save(toDo);
    }

    @Override
    public void deleteToDo(String  email) {

    }
}
