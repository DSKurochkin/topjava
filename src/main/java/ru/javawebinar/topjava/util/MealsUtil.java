package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MealsUtil {

    public static List<MealTo> filteredByCycles(Map<Integer, Meal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> sumCaloriesOfDay = new HashMap<>();

        for (Meal meal : meals.values()) {
            sumCaloriesOfDay.merge(meal.getDateTime().toLocalDate(), meal.getCalories(), Integer::sum);
        }
        List<MealTo> res = new ArrayList<>();
        for (Map.Entry<Integer, Meal> entry : meals.entrySet()) {
            if (TimeUtil.isBetweenHalfOpen(entry.getValue().getDateTime().toLocalTime(), startTime, endTime)) {
                res.add(createTo(entry.getKey(), entry.getValue()
                        , sumCaloriesOfDay.get(entry.getValue().getDateTime().toLocalDate()) > caloriesPerDay));
            }
        }
        return res;
    }

    private static MealTo createTo(int id, Meal meal, boolean excess) {
        return new MealTo(id, meal.getDateTime(), meal.getDescription(), meal.getCalories()
                , excess);
    }
}
