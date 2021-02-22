package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.DAO.MealDAO;
import ru.javawebinar.topjava.DAO.MealDaoQuasiDBImpl;
import ru.javawebinar.topjava.model.Meal;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class showMealFormServlet extends HttpServlet {
    MealDAO dao = MealDaoQuasiDBImpl.getInstance();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String id = request.getParameter("mealId");
        Meal meal = null;
        if (id != null) {
            meal = dao.getMealByID(Integer.parseInt(id));
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/meal-info.jsp");
        request.setAttribute("meal", meal);
        dispatcher.forward(request, response);
    }
}
