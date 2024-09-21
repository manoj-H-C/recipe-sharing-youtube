package com.recipe.service;

import com.recipe.config.JwtProvider;
import com.recipe.model.User;
import com.recipe.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;



    @Override
    public User findUserById(Long userId) throws Exception {
        Optional<User> opt = userRepository.findById(userId);
        if (opt.isPresent()) {
            return opt.get();
        }
        throw new Exception("User not found with id: " + userId);
    }

    @Override
    public User findUserByJwt(String jwt) throws Exception {
        String email= jwtProvider.getEmailFromJwtToken(jwt);
        if(email == null){
            throw new Exception("provide valid jwt token");
        }
        User user = userRepository.findByEmail(email);

        if(user==null){
            throw new Exception("user not found with email"+ email);
        }
        return  user;
    }

}

