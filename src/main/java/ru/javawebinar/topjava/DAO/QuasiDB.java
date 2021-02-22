package ru.javawebinar.topjava.DAO;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class QuasiDB {
//    public static final int caloriesPerDay=2000;
//    public static List<Meal> meals = new CopyOnWriteArrayList<>();
//    private static AtomicInteger count=new AtomicInteger(0);
//
//    static int increment(){
//        return count.incrementAndGet();
//    }
//    static {
//        meals.add(new Meal(increment(), LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
//        meals.add(new Meal(increment(), LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
//        meals.add(new Meal(increment(), LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
//        meals.add(new Meal(increment(), LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
//        meals.add(new Meal(increment(), LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
//        meals.add(new Meal(increment(), LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
//        meals.add(new Meal(increment(), LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
//    }
    public static final int CALORIES_PER_DAY =2000;
    private List<Meal> meals = new CopyOnWriteArrayList<>();
    private AtomicInteger count=new AtomicInteger(0);
    List<Meal> getMeals(){
        return meals;
    }

        public QuasiDB(){
        meals.add(new Meal(increment(), LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        meals.add(new Meal(increment(), LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        meals.add(new Meal(increment(), LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        meals.add(new Meal(increment(), LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        meals.add(new Meal(increment(), LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        meals.add(new Meal(increment(), LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        meals.add(new Meal(increment(), LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
    }
    int increment(){
        return count.incrementAndGet();
    }
}
