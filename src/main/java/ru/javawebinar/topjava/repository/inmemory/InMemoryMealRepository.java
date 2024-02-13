package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Integer DEFAULT_USER_ID = 1;
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);
    
    {
        MealsUtil.meals.forEach(m -> this.save(m, DEFAULT_USER_ID));
    }
    
    @Override
    public synchronized Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(userId);
            repository.put(meal.getId(), meal);
            return meal;
        }
        // handle case: attempt to update meal that doesn't belong to user
        return get(meal.getId(), userId) != null ?
                // handle case: update, but not present in storage
                repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal) : null;
    }
    
    @Override
    public boolean delete(int id, int userId) {
        //TODO можно записать в один тернарный оператор
        Meal meal = repository.get(id);
        if (meal != null) {
            return (meal.getUserId() == userId && repository.remove(id) != null);
        }
        return false;
    }
    
    @Override
    //TODO можно записать в один тернарный оператор
    public Meal get(int id, int userId) {
        Meal meal = repository.get(id);
        if (meal != null) {
            return meal.getUserId() == userId ? meal : null;
        }
        return null;
    }
    
    @Override
    public Collection<Meal> getAll(int userId) {
        return repository.values().stream()
                .filter(meal -> meal.getUserId().equals(userId))
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }
    
    @Override
    public Collection<Meal> getAllBetweenDates(int userId,
                                               LocalDateTime start,
                                               LocalDateTime end
    ) {
        Predicate<Meal> mealFilterConditions = meal -> meal.getUserId().equals(userId) && DateTimeUtil.isBetweenHalfOpen(meal.getDateTime(), start, end);
        return repository.values().stream()
                .filter(mealFilterConditions)
                .filter(meal -> DateTimeUtil.isBetweenHalfOpen(meal.getDateTime(), start, end))
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }
    
}

