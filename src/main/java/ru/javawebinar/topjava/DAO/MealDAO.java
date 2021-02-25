package ru.javawebinar.topjava.DAO;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealDAO {

    void insert(Meal meal);

    void update(int id, Meal updMeal);

    List<Meal> getAll();

    void delete(int id);

    Meal getByID(int id);

}
