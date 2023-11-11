package com.fileisland.datakeeper.Controller;

import com.fileisland.datakeeper.Dao.Entity.User;
import com.fileisland.datakeeper.Model.AuthModel;
import com.fileisland.datakeeper.Services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController()
public class AuthenticationController {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;
//    @Autowired
//    private AuthenticationManagerBuilder authenticationManagerBuilder;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody  AuthModel authModel){
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(authModel.login(), authModel.password());
        Authentication authentication = authenticationManager.authenticate(token);
        User user = (User) authentication.getPrincipal();

        return ResponseEntity.ok(jwtService.generateToken(user));
    }
}
