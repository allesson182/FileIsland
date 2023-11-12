package com.fileisland.datakeeper.Services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fileisland.datakeeper.Dao.Entity.User;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;

import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String SECRET;

    @Autowired
    private UserService userService;

    private static Logger LOGGER = LoggerFactory.getLogger(JwtService.class);
    public String generateToken(User user){
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            return JWT.create()
                    .withIssuer("API FileIsland")
                    .withSubject(user.getUsername())
                    .withClaim("id", user.getId())
                    .withClaim("email", user.getEmail())
                    //now plus 2 hours
                    .withExpiresAt(new Date(System.currentTimeMillis() + 7200000))
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new RuntimeException("Error creating token", exception);
        }

    }

    public String getUsernameFromToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            return JWT.require(algorithm)
                    // specify an specific claim validations
                    .withIssuer("API FileIsland")
                    // reusable verifier instance
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception){
            LOGGER.error("Error verifying token");
            throw new RuntimeException("Error verifying token", exception);
        }
    }

    public User GetUserFromToken(String token){
       return userService.findByUsername(this.getUsernameFromToken(token));
    }
}
