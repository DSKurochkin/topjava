package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;


public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

    private MealRestController mrc;
    private ConfigurableApplicationContext appCtx;

    @Override
    public void init() {
        appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        mrc = appCtx.getBean("mealRestController", MealRestController.class);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");

        Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));

        if (meal.isNew()) {
            log.info("Create {}", meal);
            mrc.create(meal);
        } else {
            log.info("Update {}", meal);
            mrc.update(meal, getId(request));
        }
        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                log.info("Delete {}", id);
                mrc.delete(id);
                response.sendRedirect("meals");
                break;
            case "create":
            case "update":
                final Meal meal = "create".equals(action) ?
                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                        mrc.get(getId(request));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;
            case "filter":
                String strStartD = request.getParameter("startD");
                LocalDate startD = strStartD.isEmpty() ? LocalDate.MIN : LocalDate.parse(strStartD);

                String strEndD = request.getParameter("endD");
                LocalDate endD = strEndD.isEmpty() ? LocalDate.MAX : LocalDate.parse(strStartD);

                String strStartT = request.getParameter("startT");
                LocalTime startT = strStartT.isEmpty() ? LocalTime.MIN : LocalTime.parse(strStartT);

                String strEndT = request.getParameter("endT");
                LocalTime endT = strEndT.isEmpty() ? LocalTime.MAX : LocalTime.parse(strEndT);
                log.info("getAllByFilter");
                request.setAttribute("meals", mrc.getAllByFilter(startD, endD, startT, endT));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;


            case "all":
            default:
                log.info("getAll");
                request.setAttribute("meals", mrc.getAll());
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }

    @Override
    public void destroy() {
        appCtx.close();
    }
}
