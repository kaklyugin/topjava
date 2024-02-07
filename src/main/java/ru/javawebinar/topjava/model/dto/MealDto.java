package ru.javawebinar.topjava.model.dto;

import lombok.Getter;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;

@Getter
public class MealDto {
    
    private final LocalDateTime dateTime;
    
    private final String description;
    
    private final int calories;
    
    public MealDto(LocalDateTime dateTime, String description, int calories) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }
}
