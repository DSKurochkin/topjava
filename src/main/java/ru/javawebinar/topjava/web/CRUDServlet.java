package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.DAO.MealDAO;
import ru.javawebinar.topjava.DAO.MealDaoQuasiDBImpl;
import ru.javawebinar.topjava.DAO.QuasiDB;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.TimeUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class CRUDServlet extends HttpServlet {
    MealDAO dao = MealDaoQuasiDBImpl.getInstance();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        LocalDateTime ldt = TimeUtil
                .convertStringToLdt(request.getParameter("datetime-local"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        Meal meal = new Meal(ldt, description, calories);
        String srtId = request.getParameter("id");
        if (srtId == null) {
            dao.insert(meal);
        } else {
            int id = Integer.parseInt(srtId);
            dao.update(id, meal);
        }
        response.sendRedirect("/topjava/meals");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String forward = "";
        String action = request.getParameter("action");
        if (action == null) {
            action = "allMeals";
        }
        String insertOrEdit = "/meal-info.jsp";
        String listMeals = "/meals.jsp";
        switch (action) {
            case "allMeals":
                List<MealTo> mealsTo = MealsUtil.filteredByStreams(dao.getAll(), LocalTime.MIN
                        , LocalTime.MAX, QuasiDB.caloriesPerDay);
                request.setAttribute("meals", mealsTo);
                forward = listMeals;
                break;
            case "delete":
                int mealId = Integer.parseInt(request.getParameter("mealId"));
                dao.delete(mealId);
                response.sendRedirect("/topjava/meals");
                return;
            case "edit":
                forward = insertOrEdit;
                int id = Integer.parseInt(request.getParameter("mealId"));
                Meal meal = dao.getByID(id);
                request.setAttribute("meal", meal);
                break;
            case "add":
                forward = insertOrEdit;
                break;
        }
        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    }
}




