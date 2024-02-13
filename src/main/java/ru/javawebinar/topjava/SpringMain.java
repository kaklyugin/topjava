package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 automatic resource management (ARM)
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(null, "userName", "email@mail.ru", "password", Role.ADMIN));
            adminUserController.create(new User(null, "John", "john@mail.ru", "password", Role.USER));
            adminUserController.create(new User(null, "Ann", "Ann@mail.ru", "password", Role.USER));
            adminUserController.getAll().forEach(System.out::println);
            Meal mealForUserWithId_1 = new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500, 1);
            MealRestController mealRestController = appCtx.getBean(MealRestController.class);
            //
            mealRestController.getAllByDateFilter(
                    LocalDate.of(2020, Month.JANUARY, 30),
                    LocalTime.of(10, 0, 0),
                    LocalDate.of(2020, Month.JANUARY, 30),
                    LocalTime.of(14, 0, 0)
            ).forEach(System.out::println);
            //
            System.out.println("mealRestController.get(2)) = " + mealRestController.get(2));
            // try to update for not existing user
            Meal mealWithId = mealRestController.create(mealForUserWithId_1);
            mealWithId.setUserId(2);
            mealRestController.update(mealWithId, mealWithId.getId());
            
        }
    }
}
