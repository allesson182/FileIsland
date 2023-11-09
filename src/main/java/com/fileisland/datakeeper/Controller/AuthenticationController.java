package com.fileisland.datakeeper.Controller;

import com.fileisland.datakeeper.Model.AuthModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController()
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
//    @Autowired
//    private AuthenticationManagerBuilder authenticationManagerBuilder;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody  AuthModel authModel){
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(authModel.login(), authModel.password());
        Authentication authentication = authenticationManager.authenticate(token);
        System.out.println(authentication.isAuthenticated());
        return ResponseEntity.ok().build();
    }
}
