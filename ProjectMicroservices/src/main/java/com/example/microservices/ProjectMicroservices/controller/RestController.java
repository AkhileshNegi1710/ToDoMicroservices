package com.example.microservices.ProjectMicroservices.controller;

import com.example.microservices.ProjectMicroservices.entities.ToDo;
import com.example.microservices.ProjectMicroservices.entities.User;
import com.example.microservices.ProjectMicroservices.services.LoginService;
import com.example.microservices.ProjectMicroservices.services.ToDoService;
import com.example.microservices.ProjectMicroservices.utilities.*;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.persistence.Id;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

//import javax.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

//Controller bean is used to manage the http request
//RestController will understand string must be string and user must be user
@org.springframework.web.bind.annotation.RestController
public class RestController {


    @Autowired
    ToDoService toDoService;

    @Autowired
    LoginService loginService;


    @RequestMapping("/hello")
    public String sayHello() {
        return "hello";   //hello will return hello.jsp / html pages (ViewResolver will check whether return string matches with html (jsp)pages)
    }


    //    Return User by filling entries in db using User Entity
//    without @valid
    @RequestMapping("/userInOutput")  //jackson library convert ---> Object.java into --->JSON message
    public User giveUser() {
//        return json adjacent data according to the User java file.
        return new User("akki@gmail.com", "Akki Negi", "Encrypted password");
    }


    //using @Valid includes (@NotNull @NotEmpty @NotBlank)
    @RequestMapping("/todoInput1")
    public String toDoInInput1(@Valid ToDo toDo) {
        return "ToDo Description " + toDo.getDescription() + " ToDo Priority " + toDo.getPriority();
    }


    //using @Valid (@NotNull @NotEmpty @NotBlank) and error message using binding result which collects the validation errors.
    @RequestMapping("/todoInput3")
    public String toDoInInput3(@Valid ToDo toDo, BindingResult bindingResult) {
//        if any API(/todoInput3) has errors
        if (bindingResult.hasErrors()) {
            return "Below are the error message if any validation fails \n " + bindingResult.toString();
        }

        return "ToDo Description " + toDo.getDescription() + " ToDo Priority " + toDo.getPriority();
    }

    //Remove @Valid and use ToDoValidator from utilities folder and keep binding Result.
    @RequestMapping("/todoInput4")
    public String toDoInInput4(ToDo toDo, BindingResult bindingResult) {
//        using ToDoValidator class
        ToDoValidator toDoValidator = new ToDoValidator();
        toDoValidator.validate(toDo, bindingResult);

//        if any API(/todoInput3) has errors
        if (bindingResult.hasErrors()) {
            return "Below are the error message if any validation fails \n " + bindingResult.toString();
        }

        return "ToDo Description " + toDo.getDescription() + " ToDo Priority " + toDo.getPriority();
    }


    //using @Valid and TodoValidator and bindingResult
    @RequestMapping("/todoInput5")
    public String toDoInInput5(@Valid ToDo toDo, BindingResult bindingResult) {
//        using ToDoValidator class
        ToDoValidator toDoValidator = new ToDoValidator();
        toDoValidator.validate(toDo, bindingResult);

//        if any API(/todoInput3) has errors
        if (bindingResult.hasErrors()) {
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

    //Login Http request and response
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<JsonResponsebody> login(@RequestParam(value = "email") String email, @RequestParam(value = "password") String pwd) {
//        success login : return String with login message + JWT Token with header of the HTTP response
//        Failed login : return error message
        try {
//            get email and password from database usng JPA
            Optional<User> userss = loginService.getUserFromDb(email, pwd);
            User user = userss.get();
            String jwt = loginService.createJWT(email, user.getName(), new Date());
//        the only case in which ther server sends the JWT to the client in the HEADER of the response
//body(new JsonResponsebody(HttpStatus.OK.value(),"Success! User Logged in"));
//                JsonResponsebody(status of server(integer using value()), string value)
            return ResponseEntity.status(HttpStatus.OK).header("jwt", jwt).body(new JsonResponsebody(HttpStatus.OK.value(), "Success! User Logged in"));
        } catch (UserNotFoundDatabaseException e1) {
//            403 forbidden error the server understood the request but refuses to authorize it
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new JsonResponsebody(HttpStatus.FORBIDDEN.value(), "Forbidden :" + e1.toString()));
        } catch (UnsupportedEncodingException e2) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new JsonResponsebody(HttpStatus.BAD_REQUEST.value(), "Bad Request :" + e2.toString()));
        }
    }


    //HttpServletRequest -->request of the client
    @RequestMapping("/toshowtodo")
    public ResponseEntity<JsonResponsebody> showToDo(HttpServletRequest httpServletRequest) {
//        1) Success: arrayList of ToDOs in the "response" attribute of the JsonResponseBody
//        2) Fail:error message
        try {
            Map<String, Object> userData = loginService.verifyJWTGetData(httpServletRequest);

//    (String) userData.get("email") type cast object(email) to (String)
            return ResponseEntity.status(HttpStatus.OK).body(new JsonResponsebody(HttpStatus.OK.value(), toDoService.getToDo((String) userData.get("email"))));
        } catch (UnsupportedEncodingException e1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new JsonResponsebody(HttpStatus.BAD_REQUEST.value(), "Bad Request: " + e1.toString()));
        } catch (UserNotLoggedException e2) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new JsonResponsebody(HttpStatus.FORBIDDEN.value(), "Forbidden: " + e2.toString()));
        } catch (ExpiredJwtException e3) {
            return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body(new JsonResponsebody(HttpStatus.GATEWAY_TIMEOUT.value(), "Session Expired: " + e3.toString()));
        }

    }


    @RequestMapping(value = "/newToDo", method = RequestMethod.POST)
    public ResponseEntity<JsonResponsebody> newToDO(HttpServletRequest httpServletRequest, @Valid ToDo toDo, BindingResult bindingResult) {
//        Success: toDoInserted into the response attribute of the JsonResponseBody
//        Fail :Error Message

        ToDoValidator validator = new ToDoValidator();
        validator.validate(toDo, bindingResult);
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new JsonResponsebody(HttpStatus.BAD_REQUEST.value(), "Data not valid" + bindingResult.toString()));

        }
        try {
            loginService.verifyJWTGetData(httpServletRequest);
            return ResponseEntity.status(HttpStatus.OK).body(new JsonResponsebody(HttpStatus.OK.value(), toDoService.addToDo(toDo)));
        } catch (UnsupportedEncodingException e1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new JsonResponsebody(HttpStatus.BAD_REQUEST.value(), "Bad Request: " + e1.toString()));
        } catch (UserNotLoggedException e2) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new JsonResponsebody(HttpStatus.FORBIDDEN.value(), "Forbidden: " + e2.toString()));
        } catch (ExpiredJwtException e3) {
            return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body(new JsonResponsebody(HttpStatus.GATEWAY_TIMEOUT.value(), "Session Expired: " + e3.toString()));
        }

    }

    @RequestMapping("/deleteToDO/{email}")
    public ResponseEntity<JsonResponsebody> deleteToDo(HttpServletRequest httpServletRequest, @PathVariable(name = "email") String toDoId) {
//        1) Success message of success
//        2) fail error message
        try {
            loginService.verifyJWTGetData(httpServletRequest);
            toDoService.deleteToDo(toDoId);
            return ResponseEntity.status(HttpStatus.OK).body(new JsonResponsebody(HttpStatus.OK.value(), "to Do Correctly Deleted"));

        }
        catch (UnsupportedEncodingException e1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new JsonResponsebody(HttpStatus.BAD_REQUEST.value(), "Bad Request: " + e1.toString()));
        } catch (UserNotLoggedException e2) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new JsonResponsebody(HttpStatus.FORBIDDEN.value(), "Forbidden: " + e2.toString()));
        } catch (ExpiredJwtException e3) {
            return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body(new JsonResponsebody(HttpStatus.GATEWAY_TIMEOUT.value(), "Session Expired: " + e3.toString()));
        }
    }
}
