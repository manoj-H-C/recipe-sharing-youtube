package com.recipe.service;

import com.recipe.model.User;
import com.recipe.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public User findUserById(Long userId) throws Exception {
        Optional<User> opt = userRepository.findById(userId);
        if (opt.isPresent()) {
            return opt.get();
        }
        throw new Exception("User not found with id: " + userId);
    }

    }

