package ru.javawebinar.topjava.DAO;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.util.List;

public interface MealDAO {

    void insert(Meal meal);

    boolean update(int id, Meal updMeal);

    List<MealTo> getAll();

    void delete(int id);

    Meal getByID(int id);

}
