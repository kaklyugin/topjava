package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.init.InitialTestDataLoader;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("initializing meals list");
        List<Meal> meals = InitialTestDataLoader.getInstance().getMeal();
        final LocalTime startTime = LocalTime.of(0, 0);
        final LocalTime endTime = LocalTime.of(23, 59);
        List<MealTo> mealTo= MealsUtil.filteredByStreams(meals,startTime,endTime,2000);
        request.setAttribute("mealTo",mealTo);
//        response.sendRedirect("meals.jsp");
        log.debug("forward to meals");
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }
    
 
    
}
