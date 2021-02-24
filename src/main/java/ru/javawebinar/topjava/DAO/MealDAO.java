package ru.javawebinar.topjava.DAO;

import ru.javawebinar.topjava.model.Meal;

import java.util.Map;

public interface MealDAO {

    void insert(Meal meal);

    void update(int id, Meal updMeal);

    Map<Integer, Meal> getAll();

    void delete(int id);

    Meal getByID(int id);

    boolean containsId(int key);


}
