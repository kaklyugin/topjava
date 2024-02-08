package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealRepository {
    
    Meal getById(int id);
    
    List<Meal> getAll();
    
    Meal add(Meal meal);
    
    Meal update(Meal meal);
    
    void deleteById(int id);
}
