package com.recipe.controller;

import com.recipe.model.User;
import com.recipe.repository.UserRepository;
import com.recipe.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @GetMapping("/api/users/profile")
    public User findUserByJwt(@RequestHeader("Authorization") String jwt) throws Exception {
        User user= userService.findUserByJwt(jwt);
        return user;
    }






}
