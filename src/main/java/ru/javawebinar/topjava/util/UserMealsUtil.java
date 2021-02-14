package ru.javawebinar.topjava.util;

import com.sun.org.apache.xpath.internal.operations.Bool;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {

    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);
        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExcess> res = new ArrayList<>();
        List<UserMeal> mealsInInterval = new ArrayList<>();
        Map<LocalDate, Integer> sumCaloriesOfDay = new HashMap<>();

        for (UserMeal meal : meals) {
            if (TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime))
                mealsInInterval.add(meal);
            sumCaloriesOfDay.merge(meal.getDateTime().toLocalDate(), meal.getCalories(), (old, cool) -> old + cool);
        }
        for (UserMeal meal : mealsInInterval) {
            if (sumCaloriesOfDay.get(meal.getDateTime().toLocalDate())>caloriesPerDay)
                res.add(convertMealTExcess(meal, true));
            else
                res.add(convertMealTExcess(meal, false));
        }
        return res;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> sumCaloriesOfDay = meals.stream()
                .collect(Collectors.toMap(meal -> ((UserMeal) meal).getDateTime().toLocalDate()
                        , meal -> ((UserMeal) meal).getCalories(),
                        (a, b) -> a + b));
        List<UserMealWithExcess> res = meals.stream()
                .filter(meal -> TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime))
                .map(meal -> convertMealTExcess(meal, sumCaloriesOfDay
                        .get(meal.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
        return res;
    }

    private static UserMealWithExcess convertMealTExcess(UserMeal userMeal, boolean isExcess) {
        return new UserMealWithExcess(userMeal.getDateTime(), userMeal.getDescription()
                , userMeal.getCalories(), isExcess);
    }
}
