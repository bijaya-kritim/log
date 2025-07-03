package com.example.demo.services;

import com.example.demo.model.Users;
import com.example.demo.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsersServices {
    @Autowired
    private UsersRepository userRepo;

    private final PasswordEncoder encoder = new BCryptPasswordEncoder();
    public void createUser(Users users){
        if(userRepo.findByEmail(users.getEmail()).isPresent()){
            throw new RuntimeException("User alrady exists");
        }
        users.setPassword(encoder.encode(users.getPassword()));
        userRepo.save(users);
    }

    public Users login(Users userLogin){
        Users user1 = userRepo.findByEmail(userLogin.getEmail()).orElseThrow(()->new RuntimeException("user not found"));
        if(encoder.matches(userLogin.getPassword(), user1.getPassword())){
            return user1;
        }else{
            throw new RuntimeException("invalid credentials");
        }
    }

}
