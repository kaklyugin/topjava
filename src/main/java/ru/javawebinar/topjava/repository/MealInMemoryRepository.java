package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.init.MealListTempDataStorage;
import ru.javawebinar.topjava.model.Meal;

public class MealInMemoryRepository implements MealRepository {
    
    @Override
    public int addMeal(Meal meal) {
        MealListTempDataStorage.getMeals().add(meal);
        return MealListTempDataStorage.getMeals().size();
    }
}
