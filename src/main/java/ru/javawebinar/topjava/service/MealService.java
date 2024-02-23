package ru.javawebinar.topjava.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {
    private final MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public List<MealTo> getAll(int userId, int caloriesPerDay) {
        return MealsUtil.getTos(repository.getAll(userId), caloriesPerDay);
    }

    public Meal getById(int mealId, int userId) {
        return checkNotFoundWithId(repository.get(mealId, userId), mealId);
    }

    public Meal create(Meal meal, int userId) {
        return repository.save(meal, userId);
    }

    public void update(Meal meal, int userId) {
        checkNotFoundWithId(repository.save(meal, userId), meal.getId());
    }

    public void delete(int id, int userId) {
        checkNotFoundWithId(repository.delete(id, userId), userId);
    }

    public List<MealTo> getAllByDateFilter(int userId,
                                           int caloriesPerDay,
                                           LocalDate startDate,
                                           LocalTime startTime,
                                           LocalDate endDate,
                                           LocalTime endTime) {
        LocalDateTime start = DateTimeUtil.calculateStartDateTime(startDate, startTime);
        LocalDateTime end = DateTimeUtil.calculateEndDateTime(endDate, endTime);
        Collection<Meal> userMealPerDates = repository.getAllBetweenDates(userId, start, end);
        return MealsUtil.getFilteredTos(userMealPerDates, caloriesPerDay, start.toLocalTime(), end.toLocalTime());
    }

}