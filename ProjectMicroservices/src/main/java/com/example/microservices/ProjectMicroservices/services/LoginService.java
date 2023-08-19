package com.example.microservices.ProjectMicroservices.services;

import com.example.microservices.ProjectMicroservices.entities.User;
import com.example.microservices.ProjectMicroservices.utilities.UserNotFoundDatabaseException;
import com.example.microservices.ProjectMicroservices.utilities.UserNotLoggedException;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletRequest;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

public interface LoginService {
    Optional<User> getUserFromDb(String email, String pwd) throws UserNotFoundDatabaseException;

    String createJWT(String email, String name, Date date) throws UnsupportedEncodingException;

    Map<String, Object> verifyJWTGetData(HttpServletRequest httpServletRequest)throws UnsupportedEncodingException, UserNotLoggedException;

}
