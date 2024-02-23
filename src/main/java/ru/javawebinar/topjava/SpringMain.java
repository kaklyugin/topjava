package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.web.meal.MealRestController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 automatic resource management (ARM)
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            MealRestController mealRestController = appCtx.getBean(MealRestController.class);
            mealRestController.getAllByDateFilter(
                    null,
                    null,
                    null,
                    null).forEach(System.out::println);
            mealRestController.getAllByDateFilter(
                    null,
                    null,
                    LocalDate.of(2020, 1, 30),
                    LocalTime.of(12, 0, 0))
                    .forEach(System.out::println);
            mealRestController.getAllByDateFilter(
                    null,
                    null,
                    null,
                    null)
                    .forEach(System.out::println);
            mealRestController.getAllByDateFilter(
                    LocalDate.of(2020, 1, 30),
                    LocalTime.of(13, 0, 0),
                    LocalDate.of(2020, 1, 31),
                    LocalTime.of(15, 0, 0))
                    .forEach(System.out::println);
            mealRestController.getAllByDateFilter(
                    LocalDate.of(2020, 1, 30),
                    null,
                    LocalDate.of(2020, 1, 30),
                    null)
                    .forEach(System.out::println);
            mealRestController.getAllByDateFilter(
                    null,
                    LocalTime.of(10, 0),
                    null,
                    LocalTime.of(13, 0))
                    .forEach(System.out::println);
        }
    }
}
