package com.example.microservices.ProjectMicroservices.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import org.hibernate.annotations.NotFound;
import org.springframework.boot.autoconfigure.web.WebProperties;

//Below is the entity table define in db
//JPA
//LOMBOK
//VALIDATION JSR-303 --> Hibernate validator   ,
// Data binding means not to consider these fields as separate elements (name, email, password)---> new User(name, email, password)

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @Column(name = "EMAIL")
    @Getter @Setter
    @NotNull @NotBlank @NotEmpty
    private String email;


    @Column(name="NAME")
    @Getter @Setter
    @NotNull @NotBlank @NotEmpty
    private String name;



    @Column(name = "PASSWORD")
    @Getter @Setter
    @NotNull @NotBlank @NotEmpty
    private String password;


}


