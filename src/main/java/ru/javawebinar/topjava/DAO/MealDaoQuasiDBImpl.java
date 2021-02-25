package ru.javawebinar.topjava.DAO;

import ru.javawebinar.topjava.model.Meal;

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
        int nextId = QuasiDB.increment();
        meal.setId(nextId);
        meals.put(nextId, meal);
    }

    @Override
    public void update(int id, Meal updMeal) {
        if (meals.containsKey(id)) {
            updMeal.setId(id);
            meals.put(id, updMeal);
        }
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(meals.values());
    }

    @Override
    public void delete(int id) {
        meals.remove(id);

    }

}
