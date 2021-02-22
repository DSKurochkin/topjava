package ru.javawebinar.topjava.DAO;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealDAO {
    void addMeal(Meal meal);
    void updateMeal(int id, Meal updMeal);
    List<Meal> getAllMeals();
    void deleteMeal(int id);
    Meal getMealByID(int id);
}
