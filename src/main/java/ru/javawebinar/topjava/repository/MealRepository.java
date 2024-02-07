package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;
import java.util.Optional;

public interface MealRepository {
    int addMeal(Meal meal);
    
    Meal getMealById(int id);
    
    void updateMeal(Meal meal);
    
    List<Meal> getAllMeals();
    
    void deleteMealById(int id);
    
}
