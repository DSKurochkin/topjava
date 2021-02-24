package ru.javawebinar.topjava.DAO;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class QuasiDB {
    static final int caloriesPerDay = 2000;
    private Map<Integer, Meal> meals = new ConcurrentHashMap<>();
    static AtomicInteger count = new AtomicInteger(0);

    Map<Integer, Meal> getMeals() {
        return meals;
    }

    public QuasiDB() {
        meals.put(increment(), new Meal(count.get(), LocalDateTime.of(2020, Month.JANUARY
                , 30, 10, 0), "Завтрак", 500));
        meals.put(increment(), new Meal(count.get(), LocalDateTime.of(2020, Month.JANUARY
                , 30, 13, 0), "Обед", 1000));
        meals.put(increment(), new Meal(count.get(), LocalDateTime.of(2020, Month.JANUARY
                , 30, 20, 0), "Ужин", 500));
        meals.put(increment(), new Meal(count.get(), LocalDateTime.of(2020, Month.JANUARY
                , 31, 0, 0), "Еда на граничное значение", 100));
        meals.put(increment(), new Meal(count.get(), LocalDateTime.of(2020, Month.JANUARY
                , 31, 10, 0), "Завтрак", 1000));
        meals.put(increment(), new Meal(count.get(), LocalDateTime.of(2020, Month.JANUARY
                , 31, 13, 0), "Обед", 500));
        meals.put(increment(), new Meal(count.get(), LocalDateTime.of(2020, Month.JANUARY
                , 31, 20, 0), "Ужин", 410));
    }

    static int increment() {
        return count.incrementAndGet();
    }

    static int currentId() {
        return count.get();
    }
}