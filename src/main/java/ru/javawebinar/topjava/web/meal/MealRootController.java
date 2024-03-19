package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Component;
import ru.javawebinar.topjava.service.MealService;

@Component
public class MealRootController extends AbstractMealController {
    public MealRootController(MealService service) {
        super(service);
    }
}
