package ru.javawebinar.topjava.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.util.Collection;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Service
public class MealService {

    private final MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal create(Meal meal, int authUserId) {
        return repository.save(meal, authUserId);
    }

    public void delete(int id, int authUserId) {

        checkNotFoundWithId(repository.delete(id, authUserId), id);
    }

    public Meal get(int id, int authUserId) {
        return checkNotFoundWithId(repository.get(id, authUserId), id);
    }


    public Collection<Meal> getAll(int authUserId) {
        return repository.getAll(authUserId);
    }

    public void update(Meal meal, int authUserId) {
        checkNotFoundWithId(repository.save(meal, authUserId), meal.getId());
    }

}