package com.example.microservices.ProjectMicroservices.utilities;

public class UserNotFoundDatabaseException extends Exception{
    public UserNotFoundDatabaseException(String message) {
        //        providing message to super class
        super(message);

    }
}
