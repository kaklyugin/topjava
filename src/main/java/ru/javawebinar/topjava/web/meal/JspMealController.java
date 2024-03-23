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

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
@RequestMapping("/meals")
public class JspMealController extends AbstractMealController {
    private static final Logger log = LoggerFactory.getLogger(JspMealController.class);
    
    public JspMealController(MealService service) {
        super(service);
    }
    
    @GetMapping("/filter")
    public String get(HttpServletRequest request, Model model) {
        log.info("get");
        LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = parseLocalTime(request.getParameter("endTime"));
        model.addAttribute("meals", super.getBetween(startDate, startTime, endDate, endTime));
        return "meals";
    }
    
    @GetMapping(value = {"new", "/{id}"})
    public String get(@PathVariable(required = false) Integer id, Model model) {
        log.info("get");
        Meal meal = (id == null) ?
                new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES),
                        "", 1000) : this.get(id);
        model.addAttribute("meal", meal);
        return "mealForm";
    }
    
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        log.info("delete");
        super.delete(id);
        return "redirect:/meals";
    }
    
    @PostMapping(value = {"new", "/{id}"}, consumes = {"*/*"})
    public String update(@PathVariable(required = false) Integer id, HttpServletRequest request) {
        log.info("update");
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
