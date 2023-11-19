package com.fileisland.datakeeper.Controller;

import com.fileisland.datakeeper.Dao.Entity.User;
import com.fileisland.datakeeper.Services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController()
public class AuthenticationController {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthModelDTO authModel){
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(authModel.login(), authModel.password());
        Authentication authentication = authenticationManager.authenticate(token);
        User user = (User) authentication.getPrincipal();
        String authToken = jwtService.generateToken(user);

        return ResponseEntity.ok(new AuthModelResponseDTO(user.getId().toString(), user.getUsername(), user.getEmail(), authToken));
    }
}
