package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.init.MealListTempDataStorage;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.repository.MealInMemoryRepository;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.MealSequenceIdGenerator;
import ru.javawebinar.topjava.util.DateUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class NewMealServlet extends HttpServlet {
    private static final Logger log = getLogger(NewMealServlet.class);
    private final MealRepository mealRepository = new MealInMemoryRepository();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("loaded NewMealServlet get");
        request.getRequestDispatcher("NewMealServlet.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("launched NewMeal doPost");
        request.setCharacterEncoding("UTF-8");
        try {
            Meal newMeal = new Meal(
                    MealSequenceIdGenerator.generate(),
                    DateUtil.toLocalDateTime(request.getParameter("meal_date")),
                    request.getParameter("description"),
                    Integer.parseInt(request.getParameter("calories"))
            );
            mealRepository.addMeal(newMeal);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            request.setAttribute("errorMessage", ex.getMessage());
            request.getRequestDispatcher("errorServlet").forward(request, response);
        }
        response.sendRedirect(request.getContextPath() + "/meals");
    }
}
