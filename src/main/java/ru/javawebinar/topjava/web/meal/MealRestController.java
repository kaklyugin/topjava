package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;

import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Controller
public class MealRestController {
    private final MealService service;
    
    public MealRestController(@Autowired MealService service) {
        this.service = service;
    }
    
    public Meal create(Meal meal) {
        return service.save(meal, authUserId());
    }
}