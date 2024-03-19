package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import java.time.temporal.*; // У меня старая IDEA - приходится руками добавлять, но после оптимизации она вечно удаляет этот импорт

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/meal")
public class JspMealController extends AbstractMealController {
    private static final Logger log = LoggerFactory.getLogger(JspMealController.class);

    public JspMealController(MealService service) {
        super(service);
    }

    @GetMapping(value = {"", "/{id}"})
    public String getMeal(@PathVariable(required = false) Integer id, Model model) {
        Meal meal = (id == null) ?
                new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES),
                        "", 1000) : this.get(id);
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @GetMapping(value = {"/delete/{id}"})
    public String deleteMeal(@PathVariable(required = false) Integer id, Model model) {
        delete(id);
        return "redirect:/meals";
    }

    @PostMapping(value = {"", "/{id}"}, consumes = {"*/*"})
    public String updateMeal(@PathVariable(required = false) Integer id, HttpServletRequest request) {
        Meal newMeal = new Meal(
                id,
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));
        if (id == null) {
            create(newMeal);
        } else {
            update(newMeal, id);
        }
        return "redirect:/meals";
    }
}
