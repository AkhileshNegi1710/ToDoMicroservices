package com.example.microservices.ProjectMicroservices.utilities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class JsonResponsebody {
    @Getter @Setter
//    map server response (html code)
    private int server;
    @Getter @Setter
//list of object like string of array etc.
    private Object response;

}

//http response --> java object ResponseEntity<JsonResponseBody>

//header(jwt)
//body - html page a jsonMessage-->JsonResponsebody(int server , Object response)
