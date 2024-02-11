package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.util.Collection;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {
    
    private final MealRepository repository;
    
    public MealService(@Autowired MealRepository repository) {
        this.repository = repository;
    }
    
    public Collection<Meal> getAll(int userId) {
        return repository.getAll(userId);
    }
    
    public Meal getById(int mealId, int userId) {
        return checkNotFoundWithId(repository.get(mealId, userId), mealId);
    }
    
    public Meal create(Meal meal, int userId) {
        return repository.save(meal, userId);
    }
}