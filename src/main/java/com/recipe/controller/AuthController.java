package com.recipe.controller;

import com.recipe.config.JwtProvider;
import com.recipe.dto.AuthResponse;
import com.recipe.dto.LoginRequest;
import com.recipe.model.User;
import com.recipe.repository.UserRepository;
import com.recipe.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserRepository userRepository;
    private final CustomUserDetailsService customUserDetailsService;

    private final JwtProvider jwtProvider;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(UserRepository userRepository, CustomUserDetailsService customUserDetailsService, JwtProvider jwtProvider, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.customUserDetailsService = customUserDetailsService;
        this.jwtProvider = jwtProvider;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/signup")
    public AuthResponse createUser(@RequestBody User user) throws Exception{
         String email = user.getEmail();
         String password= user.getPassword();
         String fullName= user.getFullName();

         User isExistEmail=userRepository.findByEmail(email);
         if(isExistEmail!=null){
             throw new Exception("Email is already used with another account");
         }

         User createUser= new User();
         createUser.setEmail(email);
         createUser.setPassword(passwordEncoder.encode(password));
         createUser.setFullName(fullName);

         User savedUser = userRepository.save(createUser);
        Authentication  authentication = new UsernamePasswordAuthenticationToken(email,password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMessage("signup success");
        return authResponse;
    }

    @PostMapping("/signin")
    public AuthResponse signinHandler(@RequestBody LoginRequest loginRequest){
        String username=loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Authentication authentication= authenticate(username,password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMessage("signup success");
        return authResponse;
    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
        if(userDetails==null){
            throw new BadCredentialsException("user not found");
        }
        if(!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new BadCredentialsException("invalid password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
    }
}
