package com.example.microservices.ProjectMicroservices.services;

import com.example.microservices.ProjectMicroservices.dao.UserDao;
import com.example.microservices.ProjectMicroservices.entities.User;
import com.example.microservices.ProjectMicroservices.utilities.EncryptUtils;
import com.example.microservices.ProjectMicroservices.utilities.JwtUtils;
import com.example.microservices.ProjectMicroservices.utilities.UserNotFoundDatabaseException;
import com.example.microservices.ProjectMicroservices.utilities.UserNotLoggedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import javax.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
@Service
public class LoginServiceImp implements LoginService {


    @Autowired
    UserDao userDao;

    @Autowired
    EncryptUtils encryptUtils;

    @Autowired
    JwtUtils jwtUtils;

    @Override
    public Optional<User> getUserFromDb(String email, String pwd) throws UserNotFoundDatabaseException {
        Optional<User> users = userDao.findByEmail(email);
        if (users.isPresent()) {
            User user = users.get();
            if (!encryptUtils.decrypt(user.getPassword()).equals(pwd)) {
                throw new UserNotFoundDatabaseException("Wrong Email and Password");
            }

        }
        return users;
    }


    @Override
    public String createJWT(String email, String name, Date date) throws UnsupportedEncodingException {
        date.setTime(date.getTime() + (300 * 1000));
        return jwtUtils.generateJwt(email, name, date);


    }

    @Override
    public Map<String, Object> verifyJWTGetData(HttpServletRequest httpServletRequest) throws UnsupportedEncodingException, UserNotLoggedException {
        String jwt = jwtUtils.getJwtFromHttpRequest(httpServletRequest);
        if (jwt == null) {
            throw new UserNotLoggedException("User not logged in");
        }

        return jwtUtils.jwt2Map(jwt);
    }
}