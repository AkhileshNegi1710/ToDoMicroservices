package com.example.microservices.ProjectMicroservices.utilities;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

    /**
     * This class provides method in order to generate and validate JSON Web Tokens
     */
    @Component
    public class JwtUtils {

    /* USEFUL LINKS:
        https://stormpath.com/blog/jwt-java-create-verify
        https://stormpath.com/blog/beginners-guide-jwts-in-java
        https://github.com/jwtk/jjwt
    */

//Below is the session of the user and validation is doing through secret key
        public String generateJwt(String email, String name, Date date) throws java.io.UnsupportedEncodingException
        {
        String jwt = Jwts.builder()
                    .setSubject(email)
                    .setExpiration(date)
                    .claim("name", name)
                    .signWith(
                            SignatureAlgorithm.HS256,
                            "myPersonalSecretKey12345".getBytes("UTF-8")
                    )
                    .compact();

            return jwt;
        }


        public Map<String, Object> jwt2Map(String jwt) throws java.io.UnsupportedEncodingException, ExpiredJwtException {
//Predefined Claims class is present that has expiration, email and name objects.
            Jws<Claims> claim = Jwts.parser()
                    .setSigningKey("myPersonalSecretKey12345".getBytes("UTF-8"))
                    .parseClaimsJws(jwt);

            String name = claim.getBody().get("name", String.class);
//            get expiration date
            Date expDate = claim.getBody().getExpiration();
            String email = claim.getBody().getSubject();

//            storing email, name , expiration date into Map<String, Object>
            Map<String, Object> userData = new HashMap<>();
            userData.put("email", email);
            userData.put("name", name);
            userData.put("exp_date", expDate);
//            Current date
            Date now = new Date();
//            if current date .after expiration date then throw error "session expired"
            if (now.after(expDate)) {
                throw new ExpiredJwtException(null, null, "Session expired!");
            }

            return userData;
        }


        /**
         * this method extracts the jwt from the header or the cookie in the Http request
         *
         * @param request
         * @return jwt
         */
//        rasied request through HttpsServeltRequest
        public String getJwtFromHttpRequest(HttpServletRequest request) {
            String jwt = null;
            if (request.getHeader("jwt") != null) {
                jwt = request.getHeader("jwt");     //token present in header
            } else if (request.getCookies() != null) {
                Cookie[] cookies = request.getCookies();   //token present in cookie
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("jwt")) {
                        jwt = cookie.getValue();
                    }
                }
            }
            return jwt;
        }

    }

