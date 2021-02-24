package ru.javawebinar.topjava.DAO;

import ru.javawebinar.topjava.model.Meal;

import java.util.Map;

public class MealDaoQuasiDBImpl implements MealDAO {
    private final QuasiDB db;
    private final Map<Integer, Meal> meals;

    public MealDaoQuasiDBImpl() {
        db = new QuasiDB();
        meals = db.getMeals();
    }

    @Override
    public Meal getByID(int id) {
        return meals.get(id);
    }

    @Override
    public void insert(Meal meal) {
        meals.put(db.increment(), meal);
    }

    @Override
    public void update(int id, Meal updMeal) {
        meals.put(id, updMeal);
    }

    @Override
    public Map<Integer, Meal> getAll() {
        return meals;
    }

    @Override
    public boolean containsId(int key) {
        return meals.containsKey(key);
    }

    @Override
    public void delete(int id) {
        meals.remove(id);
    }

}
