package ru.javawebinar.topjava.DAO;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public class MealDaoQuasiDBImpl implements MealDAO {
    private static MealDaoQuasiDBImpl instance;
    private final QuasiDB DB;
    private final List<Meal> MEALS;

    private MealDaoQuasiDBImpl() {
        DB=new QuasiDB();
        MEALS=DB.getMeals();
    }

    public static MealDaoQuasiDBImpl getInstance() {
        if (instance == null) {
            instance= new MealDaoQuasiDBImpl();
        }
        return instance;
    }

    @Override
    public Meal getByID(int id) {
        return MEALS.stream().filter(meal -> meal.getId() == id).findAny()
                .orElse(null);
    }

    @Override
    public void insert(Meal meal) {
        meal.setId(DB.increment());
        MEALS.add(meal);
    }

    @Override
    public void update(int id, Meal updMeal) {
        int i;
        for (i = 0; i < MEALS.size(); i++) {
            if (MEALS.get(i).getId() == id) {
                break;
            }
        }
        updMeal.setId(id);
        MEALS.set(i, updMeal);
    }

    @Override
    public List<Meal> getAll() {

        return MEALS;
    }

    @Override
    public void delete(int id) {
        MEALS.remove(getByID(id));

    }

}