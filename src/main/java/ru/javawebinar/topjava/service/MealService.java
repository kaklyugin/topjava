package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

@Service
public class MealService {
    
    private final MealRepository repository;
    
    public MealService(@Autowired MealRepository repository) {
        this.repository = repository;
    }
    
    public Meal save(Meal meal, int userId) {
        return checkMealBelongsToUser(meal, userId) ? repository.save(meal, userId) : null;
    }
    
    private boolean checkMealBelongsToUser(Meal meal, int authorizedUserId) {
        if (meal.getUserId() != authorizedUserId) {
            throw new NotFoundException(String.format("User with id %s has no permissions for operations with selected meal", authorizedUserId));
        }
        return true;
    }
}