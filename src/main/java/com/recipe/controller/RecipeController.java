package com.recipe.controller;

import com.recipe.model.Recipe;
import com.recipe.model.User;
import com.recipe.service.RecipeService;
import com.recipe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RecipeController {

    private final RecipeService recipeService;
    private final UserService userService;

    @Autowired
    public RecipeController(RecipeService recipeService, UserService userService) {
        this.recipeService = recipeService;
        this.userService = userService;
    }
    @PostMapping("/api/recipe/user/{userId}")
    public Recipe createRecipe(@RequestBody Recipe recipe, @PathVariable Long userId) throws Exception {
        User user = userService.findUserById(userId);
        Recipe newRecipe = recipeService.createRecipe(recipe,user);
        return newRecipe;
    }

    @GetMapping("/api/recipe")
    public List<Recipe> getAllResponse() throws Exception{
        List<Recipe> recipes = recipeService.findAllRecipe();
        return recipes;
    }

    @DeleteMapping("/api/recipe/{recipeId}")
    public String deleteRecipe(@PathVariable Long recipeId) throws Exception{
        recipeService.deleteRecipe(recipeId);
        return "recipe deleted successfully";
    }

    @PutMapping("/api/recipe/{id}")
    public Recipe updateRecipe(@RequestBody Recipe recipe, @PathVariable Long id) throws Exception{
        Recipe updatedRecipe=recipeService.updateRecipe(recipe,id);
        return updatedRecipe;
    }

    @PutMapping("/api/recipe/{id}/user/{userId}")
    public Recipe likeRecipe(@PathVariable Long userId, @PathVariable Long id) throws Exception{
        User user = userService.findUserById(userId);
        Recipe updatedRecipe = recipeService.likeRecipe(id,user);
        return updatedRecipe;
    }
}
