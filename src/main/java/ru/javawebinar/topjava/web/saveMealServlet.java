package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.DAO.MealDAO;
import ru.javawebinar.topjava.DAO.MealDaoQuasiDBImpl;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.TimeUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

public class saveMealServlet extends HttpServlet {
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

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
