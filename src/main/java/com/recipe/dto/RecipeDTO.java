package com.recipe.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecipeDTO {
    private Long id;
    private String title;
    private String image;
    private String description;
    private boolean vegetarian; // Fixed typo from "vegitarian" to "vegetarian"
    private LocalDateTime createdAt;
    private List<Long> likes;
}
