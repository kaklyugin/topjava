package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.UserTestData.GUEST_ID;

public class MealTestData {
    public static final int BREAKFAST_30_ID = GUEST_ID + 1;
    public static final int LUNCH_30_ID = BREAKFAST_30_ID + 1;
    public static final int DINNER_30_ID = LUNCH_30_ID + 1;
    public static final int BOUNDARY_MEAL_31_ID = DINNER_30_ID + 1;
    public static final int BREAKFAST_31_ID = BOUNDARY_MEAL_31_ID + 1;
    public static final int LUNCH_31_ID = BREAKFAST_31_ID + 1;
    public static final int DINNER_31_ID = LUNCH_31_ID + 1;

    public static final Meal breakfast_30 = new Meal(BREAKFAST_30_ID, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500);
    public static final Meal lunch_30 = new Meal(LUNCH_30_ID, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000);
    public static final Meal dinner_30 = new Meal(DINNER_30_ID, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500);
    public static final Meal boundary_meal_31 = new Meal(BOUNDARY_MEAL_31_ID, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100);
    public static final Meal breakfast_31 = new Meal(BREAKFAST_31_ID, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000);
    public static final Meal lunch_31 = new Meal(LUNCH_31_ID, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500);
    public static final Meal dinner_31 = new Meal(DINNER_31_ID, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410);

    public static Meal getNew() {
        return new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 01), "New", 999);
    }

    public static Meal getUpdated() {
        return new Meal(BREAKFAST_30_ID, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 15), "Updated", 700);
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }
}
