package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealInMemoryRepository;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

public class UpdateMealServlet extends HttpServlet {
    private static final Logger log = getLogger(UpdateMealServlet.class);
    private final MealRepository mealRepository = MealInMemoryRepository.getInstance();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("loaded Update Meal Servlet get");
        try {
            final int mealId = Integer.parseInt(request.getParameter("mealId"));
            final Meal mealForUpdate = mealRepository.getById(mealId);
            request.setAttribute("mealForUpdate", mealForUpdate);
            request.getRequestDispatcher("updateMeal.jsp").forward(request, response);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            request.setAttribute("errorMessage", ex.getMessage());
            request.getRequestDispatcher("errorServlet").forward(request, response);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("launched UpdateMealServlet doPost");
        request.setCharacterEncoding("UTF-8");
        try {
            Meal newMeal = new Meal(
                    Integer.parseInt(request.getParameter("mealId")),
                    DateUtil.toLocalDateTime(request.getParameter("meal_date")),
                    request.getParameter("description"),
                    Integer.parseInt(request.getParameter("calories"))
            );
            mealRepository.update(newMeal);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            request.setAttribute("errorMessage", ex.getMessage());
            response.sendRedirect("error.jsp");
        }
        response.sendRedirect(request.getContextPath() + "/meals");
    }
}
