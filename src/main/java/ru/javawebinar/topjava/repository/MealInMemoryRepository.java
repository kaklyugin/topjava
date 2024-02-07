package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.dto.MealDto;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class MealInMemoryRepository implements MealRepository {
    
    private final List<Meal> meals = Collections.synchronizedList(new ArrayList<>());
    private static MealInMemoryRepository INSTANCE;
    private static final AtomicInteger sequence = new AtomicInteger(1);
    
    private static int generate() {
        return sequence.getAndIncrement();
    }
    
    private MealInMemoryRepository() {
        meals.add(new Meal(generate(), LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        meals.add(new Meal(generate(), LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        meals.add(new Meal(generate(), LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        meals.add(new Meal(generate(), LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        meals.add(new Meal(generate(), LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        meals.add(new Meal(generate(), LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        meals.add(new Meal(generate(), LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
    }
    
    public static MealInMemoryRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MealInMemoryRepository();
        }
        return INSTANCE;
    }
    
    @Override
    public Meal getById(int id) {
        return meals.stream()
                .filter(m -> m.getId() == id)
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }
    
    @Override
    public synchronized Meal add(MealDto mealDto) {
        int id = generate();
        meals.add(Meal.of(id, mealDto));
        return meals.stream()
                .filter(m -> m.getId() == m.getId())
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }
    
    @Override
    public synchronized Meal update(Meal meal) {
        deleteById(meal.getId());
        add(meal);
        return meals.stream()
                .filter(m -> m.getId() == m.getId())
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }
    
    @Override
    public List<Meal> getAll() {
        return meals.stream()
                .sorted(Comparator.comparing(Meal::getId))
                .collect(Collectors.toList());
    }
    
    @Override
    public synchronized void deleteById(int id) {
        this.meals.removeIf(meal -> meal.getId() == id);
    }
    
    @Override
    public Meal add(Meal meal) {
        meals.add(meal);
        return meals.stream()
                .filter(m -> m.getId() == m.getId())
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }
}
