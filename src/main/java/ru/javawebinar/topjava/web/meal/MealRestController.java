package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.service.MealService;

@Service
public class MealRestController extends AbstractMealController {
    public MealRestController(MealService service) {
        super(service);
    }
}
