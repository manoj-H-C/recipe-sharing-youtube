package com.recipe.repository;

import com.recipe.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe,Long> {
}
