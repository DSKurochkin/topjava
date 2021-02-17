package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.DB.QuasiDB;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;
import java.util.List;

public class MealServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<MealTo> mealsTo = MealsUtil.filteredByStreams(QuasiDB.meals, LocalTime.MIN
                , LocalTime.MAX, QuasiDB.caloriesPerDay);
        request.setAttribute("meals", mealsTo);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/meals.jsp");
        dispatcher.forward(request, response);
    }
}
