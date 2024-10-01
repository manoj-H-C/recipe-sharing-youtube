package com.recipe.controller;

import com.recipe.model.Recipe;
import com.recipe.model.User;
import com.recipe.service.RecipeService;
import com.recipe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/recipes")
public class RecipeController {

    private final RecipeService recipeService;
    private final UserService userService;

    @Autowired
    public RecipeController(RecipeService recipeService, UserService userService) {
        this.recipeService = recipeService;
        this.userService = userService;
    }
    @PostMapping()
    public Recipe createRecipe(@RequestBody Recipe recipe, @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwt(jwt);
        Recipe newRecipe = recipeService.createRecipe(recipe,user);
        return newRecipe;
    }

    @GetMapping()
    public List<Recipe> getAllResponse() throws Exception{
        List<Recipe> recipes = recipeService.findAllRecipe();
        return recipes;
    }

//    @DeleteMapping("/{recipeId}")
////    public String deleteRecipe(@PathVariable Long recipeId) throws Exception{
////        recipeService.deleteRecipe(recipeId);
////        return "recipe deleted successfully";
////    }

    @DeleteMapping("/{recipeId}")
    public ResponseEntity<Map<String, String>> deleteRecipe(@PathVariable Long recipeId) throws Exception {
        recipeService.deleteRecipe(recipeId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "recipe deleted successfully");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public Recipe updateRecipe(@RequestBody Recipe recipe, @PathVariable Long id) throws Exception{
        Recipe updatedRecipe=recipeService.updateRecipe(recipe,id);
        return updatedRecipe;
    }

    @PutMapping("/{id}/like")
    public Recipe likeRecipe(@RequestHeader("Authorization") String jwt, @PathVariable Long id) throws Exception{
        User user = userService.findUserByJwt(jwt);
        Recipe updatedRecipe = recipeService.likeRecipe(id,user);
        return updatedRecipe;
    }
}
