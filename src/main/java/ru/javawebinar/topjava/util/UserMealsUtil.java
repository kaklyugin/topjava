package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

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
//        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
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
        // TODO Implement by streams
        return null;
    }
}
