package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );
        
        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);
        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }
    
    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // Сумма калорий за день
        Map<LocalDate, Integer> dayCaloriesTotalSum = new HashMap<>();
        // Считаем суммарное кол-во калорий за день
        meals.forEach(
                meal ->
                        dayCaloriesTotalSum.merge(meal.getDateTime().toLocalDate(), meal.getCalories(), Integer::sum)
        );
        // Массив с результатом фильтрации и флагом превышения калорий
        List<UserMealWithExcess> resultingMealsList = new ArrayList<>();
        meals.forEach(
                meal ->
                {
                    boolean isInTimeRange = TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime);
                    boolean caloriesPerDayWereExceeded = dayCaloriesTotalSum.get(meal.getDateTime().toLocalDate()) > caloriesPerDay;
                    if (isInTimeRange) {
                        resultingMealsList.add(
                                new UserMealWithExcess(
                                        meal.getDateTime(),
                                        meal.getDescription(),
                                        meal.getCalories(),
                                        caloriesPerDayWereExceeded
                                ));
                    }
                }
        );
        return resultingMealsList;
    }
    
    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        
        Map<LocalDate, Boolean> dayCaloriesExceeding = calculateIfDayMealCaloriesAreExceeded(meals, caloriesPerDay);
        List<UserMeal> mealsFilteredByTimeRange = filterMealsByTimeRange(meals, startTime, endTime);
        return mealsFilteredByTimeRange.stream()
                .map(
                        
                        meal -> createUserMealsWithExcess(meal, dayCaloriesExceeding.get(meal.getDateTime().toLocalDate()))
                
                ).collect(Collectors.toList());
        
    }
    
    private static Map<LocalDate, Boolean> calculateIfDayMealCaloriesAreExceeded(List<UserMeal> meals, int caloriesPerDay) {
        Map<LocalDate, Integer> mealCaloriesPerDayTotalSum =
                meals.stream()
                        .collect(
                                groupingBy(
                                        meal -> meal.getDateTime().toLocalDate(),
                                        summingInt(UserMeal::getCalories))
                        );
        
        return mealCaloriesPerDayTotalSum.entrySet().stream()
                .collect(
                        Collectors.toMap(
                                Map.Entry::getKey,
                                entry -> entry.getValue() > caloriesPerDay)
                );
        
    }
    
    private static List<UserMeal> filterMealsByTimeRange(List<UserMeal> meals, LocalTime startTime, LocalTime endTime) {
        return meals.stream()
                .filter(meal -> TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime))
                .collect(Collectors.toList());
    }
    
    private static UserMealWithExcess createUserMealsWithExcess(UserMeal meal, boolean isDayCaloriesLimitExceeded) {
        return new UserMealWithExcess(
                meal.getDateTime(),
                meal.getDescription(),
                meal.getCalories(),
                isDayCaloriesLimitExceeded);
    }
    
}
