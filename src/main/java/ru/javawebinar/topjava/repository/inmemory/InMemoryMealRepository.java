package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
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
    public Meal save(Meal meal, int userId) {
        //handle case: new meal
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(userId);
            repository.put(meal.getId(), meal);
            return meal;
        }
        // handle case: update meal
        return get(meal.getId(), userId) != null ?
                repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal) : null;
    }
    
    @Override
    public boolean delete(int id, int userId) {
        return repository.remove(id, get(id, userId));
    }
    
    @Override
    public Meal get(int id, int userId) {
        return (repository.get(id) != null && repository.get(id).getUserId() == userId)
                ? repository.get(id) : null;
    }
    
    @Override
    public List<Meal> getAll(int userId) {
        return filterByPredicate(meal -> meal.getUserId().equals(userId));
    }
    
    @Override
    public List<Meal> getAllBetweenDates(int userId,
                                         LocalDateTime start,
                                         LocalDateTime end
    ) { // TODO Я вот тут не знаю, как лучше - можно в принципе переиспользовать getAll, но тогда надо сюда добавить
        // входной параметр filterByPredicate(Predicate<Meal> filter)
        // подскажите, как лучше ?
        return filterByPredicate(meal -> meal.getUserId().equals(userId)
                && DateTimeUtil.isBetweenHalfOpen(meal.getDateTime(), start, end));
    }
    
    private List<Meal> filterByPredicate(Predicate<Meal> filter) {
        return repository.values().stream()
                .filter(filter)
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}

