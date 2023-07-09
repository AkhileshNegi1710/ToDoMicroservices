package com.example.microservices.ProjectMicroservices.utilities;

public class UserNotLoggedException extends Exception{
    public UserNotLoggedException(String message) {
//        providing message to super class
        super(message);
    }
}
