package com.fileisland.datakeeper.Controller;

import com.fileisland.datakeeper.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;

@Controller("/user")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping
    public ResponseEntity getUserById(Long id){
        return ResponseEntity.ok(userService.findById(id));
    }

    @GetMapping("/all")
    public ResponseEntity getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PutMapping("/updateEmail")
    public ResponseEntity updateEmail(Long userId, String email){
        return ResponseEntity.ok(userService.updateEmail(userId, email));
    }
    @PutMapping("/updatePassword")
    public ResponseEntity updatePassword(Long userId, String password){
        userService.updateUserPassword(userId, password);
        return ResponseEntity.ok().build();
    }


}
