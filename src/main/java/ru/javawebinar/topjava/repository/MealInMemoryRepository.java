package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MealInMemoryRepository implements MealRepository {
    
    private final List<Meal> meals = Collections.synchronizedList(new ArrayList<>());
    private static MealInMemoryRepository INSTANCE;
    
    private MealInMemoryRepository() {
        meals.add(new Meal(MealSequenceIdGenerator.generate(), LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        meals.add(new Meal(MealSequenceIdGenerator.generate(), LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        meals.add(new Meal(MealSequenceIdGenerator.generate(), LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        meals.add(new Meal(MealSequenceIdGenerator.generate(), LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        meals.add(new Meal(MealSequenceIdGenerator.generate(), LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        meals.add(new Meal(MealSequenceIdGenerator.generate(), LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        meals.add(new Meal(MealSequenceIdGenerator.generate(), LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
    }
    
    public static MealInMemoryRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MealInMemoryRepository();
        }
        return INSTANCE;
    }
    
    @Override
    public Meal getMealById(int id) {
        return meals.stream()
                .filter(m -> m.getId() == id)
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }
    
    @Override
    public int addMeal(Meal meal) {
        meals.add(meal);
        return meals.size();
    }
    
    @Override
    public synchronized void updateMeal(Meal meal) {
        deleteMealById(meal.getId());
        addMeal(meal);
    }
    
    @Override
    public List<Meal> getAllMeals() {
        return meals.stream()
                .sorted(Comparator.comparing(Meal::getId))
                .collect(Collectors.toList());
    }
    
    @Override
    public synchronized void deleteMealById(int id) {
        this.meals.removeIf(meal -> meal.getId() == id);
    }
}
