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
    public Meal getMealByID(int id) {
        return MEALS.stream().filter(meal -> meal.getId() == id).findAny()
                .orElse(null);
    }

    @Override
    public void addMeal(Meal meal) {
        meal.setId(DB.increment());
        MEALS.add(meal);
    }

    @Override
    public void updateMeal(int id, Meal updMeal) {
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
    public List<Meal> getAllMeals() {

        return MEALS;
    }

    @Override
    public void deleteMeal(int id) {
        MEALS.remove(getMealByID(id));

    }

}
