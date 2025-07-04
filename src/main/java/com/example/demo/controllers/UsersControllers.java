package com.example.demo.controllers;

import com.example.demo.model.Users;
import com.example.demo.services.UsersServices;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class UsersControllers {
    @Autowired
    private UsersServices usersServices;

    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody Users users){
        usersServices.createUser(users);
        return ResponseEntity.ok("created user successfully");
    }
    @PostMapping("/login")
    public ResponseEntity<String> logIn(@RequestBody Users usersLogin, HttpSession session){
        Users user = usersServices.login(usersLogin);
        session.setAttribute("userId", user.getUserId());
        return ResponseEntity.ok("Log in successful");
    }
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        if (session.getAttribute("userId") != null) {
            session.invalidate();  // Destroy session
            return ResponseEntity.ok("Logged out successfully");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No user is logged in");
        }
    }

}
