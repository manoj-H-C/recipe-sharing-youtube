package com.recipe.service;

import com.recipe.model.User;

public interface UserService {
    User findUserById(Long userId) throws Exception;
}
