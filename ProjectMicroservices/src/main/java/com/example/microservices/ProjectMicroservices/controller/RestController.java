package com.example.microservices.ProjectMicroservices.controller;

import com.example.microservices.ProjectMicroservices.entities.ToDo;
import com.example.microservices.ProjectMicroservices.entities.User;
import com.example.microservices.ProjectMicroservices.utilities.JsonResponsebody;
import com.example.microservices.ProjectMicroservices.utilities.JwtUtils;
import com.example.microservices.ProjectMicroservices.utilities.ToDoValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

//Controller bean is used to manage the http request
//RestController will understand string must be string and user must be user
@org.springframework.web.bind.annotation.RestController
public class RestController {

    @Autowired
    private JsonResponsebody jsonResponsebody;

    @Autowired
    private JwtUtils jwtUtils;



    @RequestMapping("/hello")
    public String sayHello()
    {
        return "hello";   //hello will return hello.jsp / html pages (ViewResolver will check whether return string matches with html (jsp)pages)
    }


//    Return User by filling entries in db using User Entity
//    without @valid
    @RequestMapping("/userInOutput")  //jackson library convert ---> Object.java into --->JSON message
    public User giveUser()
    {
//        return json adjacent data according to the User java file.
    return new User("akki@gmail.com","Akki Negi","Encrypted password");
    }


//using @Valid includes (@NotNull @NotEmpty @NotBlank)
    @RequestMapping("/todoInput1")
    public String toDoInInput1(@Valid ToDo toDo)
    {
     return "ToDo Description " + toDo.getDescription() + " ToDo Priority " + toDo.getPriority();
    }


//using @Valid (@NotNull @NotEmpty @NotBlank) and error message using binding result which collects the validation errors.
    @RequestMapping("/todoInput3")
    public String toDoInInput3(@Valid ToDo toDo, BindingResult bindingResult)
    {
//        if any API(/todoInput3) has errors
        if(bindingResult.hasErrors())
        {
            return "Below are the error message if any validation fails \n " + bindingResult.toString();
        }

        return "ToDo Description " + toDo.getDescription() + " ToDo Priority " + toDo.getPriority();
    }

//Remove @Valid and use ToDoValidator from utilities folder and keep binding Result.
    @RequestMapping("/todoInput4")
    public String toDoInInput4(ToDo toDo, BindingResult bindingResult)
    {
//        using ToDoValidator class
        ToDoValidator toDoValidator =new ToDoValidator();
        toDoValidator.validate(toDo,bindingResult);

//        if any API(/todoInput3) has errors
        if(bindingResult.hasErrors())
        {
            return "Below are the error message if any validation fails \n " + bindingResult.toString();
        }

        return "ToDo Description " + toDo.getDescription() + " ToDo Priority " + toDo.getPriority();
    }




//using @Valid and TodoValidator and bindingResult
    @RequestMapping("/todoInput5")
    public String toDoInInput5(@Valid ToDo toDo, BindingResult bindingResult)
    {
//        using ToDoValidator class
        ToDoValidator toDoValidator =new ToDoValidator();
        toDoValidator.validate(toDo,bindingResult);

//        if any API(/todoInput3) has errors
        if(bindingResult.hasErrors())
        {
            return "Below are the error message if any validation fails \n " + bindingResult.toString();
        }

        return "ToDo Description " + toDo.getDescription() + " ToDo Priority " + toDo.getPriority();
    }

//ResponseEntity which helps in providing flexible response not fixed like String, User class(single object)
//@RequestMapping("/exampleURL")
//    public ResponseEntity<JsonResponsebody> returnStandardResponse()
//{
//
//    return ResponseEntity.status(HttpStatus.OK).header("jwt",jwt).body(new JsonResponsebody());
//}


}

