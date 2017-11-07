package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.daoImpl.MealDaoImplInMemory;
import ru.javawebinar.topjava.model.Meal;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    MealDao dao = new MealDaoImplInMemory();
    String INSERT_OR_EDIT = "/addOrEdit.jsp";
    String LIST_MEALS = "/meals.jsp";
    String forward;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action.equalsIgnoreCase("delete")) {
            int userId = Integer.parseInt(request.getParameter("mealId"));
            dao.delete(userId);
            request.setAttribute("meals", dao.getAllMeals());
            forward = LIST_MEALS;
            log.debug("delete meal id =" + userId);
        } else if (action.equalsIgnoreCase("edit")) {
            int mealId = Integer.parseInt(request.getParameter("mealId"));
            Meal meal = dao.getMeal(mealId);
            request.setAttribute("meal", meal);
            forward = INSERT_OR_EDIT;
            log.debug("insert or edit meal");
        } else if (action.equalsIgnoreCase("listMeals")) {
            request.setAttribute("meals", dao.getAllMeals());
            forward = LIST_MEALS;
            log.debug("list meal");
        } else {
            forward = INSERT_OR_EDIT;
        }

        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String inputModified = request.getParameter("localDateTime").replace("T", " ");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime localDateTime = LocalDateTime.parse(inputModified, formatter);
        String description = request.getParameter("description");
        Integer calories = Integer.parseInt(request.getParameter("calories"));
        int id = dao.getCounter().get();
        try {
            id = Integer.parseInt(request.getParameter("mealId"));
        } catch (Exception e) {
        }
        dao.edit(new Meal(localDateTime, description, calories, id));
        RequestDispatcher view = request.getRequestDispatcher(LIST_MEALS);
        request.setAttribute("mealsList", dao.getAllMeals());
        view.forward(request, response);
    }
}