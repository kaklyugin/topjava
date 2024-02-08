package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.repository.MealInMemoryRepository;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

public class DeleteMealServlet extends HttpServlet {
    private static final Logger log = getLogger(DeleteMealServlet.class);
    private final MealRepository mealRepository = MealInMemoryRepository.getInstance();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("entered DeleteMealServlet doDelete");
        try {
            mealRepository.deleteById(Integer.parseInt(request.getParameter("mealId")));
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            request.setAttribute("errorMessage", ex.getMessage());
            response.sendRedirect("error.jsp");
        }
        response.sendRedirect(request.getContextPath() + "/meals");
    }
}
