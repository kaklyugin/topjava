package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class MealInMemoryRepository implements MealRepository {
    
    private static MealInMemoryRepository INSTANCE;
    
    private static final AtomicInteger sequence = new AtomicInteger(1);
    
    private final Map<Integer, Meal> meals = new ConcurrentHashMap<>();

    private MealInMemoryRepository() {
        add(new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        add(new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        add(new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        add(new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        add(new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        add(new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        add(new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
    }

    public static MealInMemoryRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MealInMemoryRepository();
        }
        return INSTANCE;
    }

    private int generate() {
        return sequence.getAndIncrement();
    }
    
    @Override
    public Meal getById(int id) {
        return meals.get(id);
    }
    
    @Override
    public Meal add(Meal meal) {
        final int mealId = meal.getId() == null ? generate() : meal.getId();
        Meal mealWithId =
                new Meal(mealId, meal.getDateTime(), meal.getDescription(), meal.getCalories());
        meals.put(mealId, mealWithId);
        return mealWithId;
    }
    
    @Override
    public Meal update(Meal meal) {
        if (meals.get(meal.getId()) == null) {
            throw new IllegalArgumentException("Failed to update meal. Meal with id = " + meal.getId() + " does not exist.");
        }
        deleteById(meal.getId());
        add(meal);
        return meal;
    }
    
    @Override
    public List<Meal> getAll() {
        return meals.values().stream()
                .sorted(Comparator.comparing(Meal::getId))
                .collect(Collectors.toList());
    }
    
    @Override
    public void deleteById(int id) {
        this.meals.remove(id);
    }
}
