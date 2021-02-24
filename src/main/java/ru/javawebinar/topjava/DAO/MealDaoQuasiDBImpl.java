package ru.javawebinar.topjava.DAO;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MealDaoQuasiDBImpl implements MealDAO {
    private final Map<Integer, Meal> meals;

    public MealDaoQuasiDBImpl() {
        meals = new QuasiDB().getMeals();
    }

    @Override
    public Meal getByID(int id) {
        return meals.get(id);
    }

    @Override
    public void insert(Meal meal) {
        meal.setId(QuasiDB.increment());
        meals.put(QuasiDB.currentId(), meal);
    }

    @Override
    public boolean update(int id, Meal updMeal) {
        if (meals.containsKey(id)) {
            updMeal.setId(id);
            meals.put(id, updMeal);
            return true;
        }
        return false;
    }

    @Override
    public List<MealTo> getAll() {
        List<Meal> all = new ArrayList<>(meals.values());

        return MealsUtil.filteredByStreams(all, LocalTime.MIN
                , LocalTime.MAX, QuasiDB.caloriesPerDay);
    }

    @Override
    public void delete(int id) {
        if (meals.containsKey(id)) {
            meals.remove(id);
        }
    }

}
