package com.recipe.service;

import com.recipe.model.Recipe;
import com.recipe.model.User;

import java.util.List;

public interface RecipeService {

    Recipe createRecipe(Recipe recipe, User user);
     Recipe findRecipeById(Long id) throws Exception;
     void deleteRecipe(Long id) throws Exception;
     Recipe updateRecipe(Recipe recipe, Long id) throws Exception;
     List<Recipe> findAllRecipe();
     Recipe likeRecipe(Long recipeId, User user) throws Exception;
}
