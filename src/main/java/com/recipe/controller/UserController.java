package com.recipe.controller;

import com.recipe.model.User;
import com.recipe.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/users")
    public User createUser(@RequestBody User user) throws Exception {
        User isExist = userRepository.findByEmail(user.getEmail());
        if(isExist!=null){
            throw new Exception("User already exists with email: "+user.getEmail());
        }
        User savedUser=userRepository.save(user);
        return savedUser;
    }
    @DeleteMapping("/users/{userId}")
    public String deleteUser(@PathVariable Long userId){
        userRepository.deleteById(userId);
        return "User Deleted successfully..!";
    }

    @GetMapping("/users/all-users")
    public List<User> getAllUsers(){
        List<User> users = userRepository.findAll();
        return users;
    }

}
