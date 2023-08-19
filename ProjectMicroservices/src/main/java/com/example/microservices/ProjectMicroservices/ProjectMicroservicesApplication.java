package com.example.microservices.ProjectMicroservices;

import com.example.microservices.ProjectMicroservices.dao.ToDoDao;
import com.example.microservices.ProjectMicroservices.dao.UserDao;
import com.example.microservices.ProjectMicroservices.entities.ToDo;
import com.example.microservices.ProjectMicroservices.entities.User;
import com.example.microservices.ProjectMicroservices.utilities.EncryptUtils;
import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;


@SpringBootApplication
public class ProjectMicroservicesApplication implements CommandLineRunner
//public class ProjectMicroservicesApplication
{

//Autowired to inject concrete class.
//UserDaoImpl
//UserDao userDao=new UserDaoImpl();
//No need of constructor when using autowired
	@Autowired
	UserDao userDao;

	@Autowired
	ToDoDao toDoDao;

	@Autowired
	EncryptUtils encryptUtils;

//	add logger
	private static final Logger log= LoggerFactory.getLogger(ProjectMicroservicesApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(ProjectMicroservicesApplication.class, args);
	}

	@Override
////three dots used in String to pass multiple arguments to be passed when function is called.
	public void run(String... strings) throws Exception
	{
//everything here is implemented before our Microservice will be available for HTTP request
		String encryptedPwd;
		encryptedPwd = encryptUtils.encrypt("akki");
		userDao.save(new User("akki@gmail.com","Akhilesh",encryptedPwd));
		encryptedPwd = encryptUtils.encrypt("prashant");
		userDao.save(new User("prashant@gmail.com","Prashant",encryptedPwd));
		encryptedPwd=encryptUtils.encrypt("ankit");
		userDao.save(new User("ankit@gmail.com","Ankit",encryptedPwd));


		toDoDao.save(new ToDo(1, "creating first microservice project with Alessandro", new Date(), "high","akki@gmail.com"));
//		We can set id and date as null bcoz @Null annotation check is not added in entity
		toDoDao.save(new ToDo(2, "creating first microservice project with Alessandro", new Date(), "high","prashant@gmail.com"));
		toDoDao.save(new ToDo(3, "creating first microservice project with Alessandro", new Date(), "high","ankit@gmail.com"));


	log.info("Lets fill H2 in-memory database");
	log.info("we've finished to fill our database");
	}
}
