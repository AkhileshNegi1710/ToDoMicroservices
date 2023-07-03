package com.example.microservices.ProjectMicroservices.entities;

import jakarta.persistence.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.Date;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Table(name = "todos")
public class ToDo {
    @Id
    @Getter @Setter
    private Integer id;

    @Column(name = "DESCRIPTION")
    @Getter @Setter
    @NotNull @NotBlank @NotEmpty
    private String description;

    @Column(name = "DATE")
    @Getter @Setter
    private Date date;

    @Column(name = "PRIORITY")
    @NotBlank @NotNull @NotEmpty
    @Getter @Setter
    private String priority;

    //    foreign key user
    @Column(name = "FK_USER")
    @Getter @Setter
    @NotNull @NotBlank @NotEmpty
    private String fkUser;

    @PrePersist
    void getTimeOperation()
    {
        this.date=new Date();
    }
}