package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(this::save);

    }

    @Override
    public Meal save(Meal meal, int authUserId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(authUserId);
            repository.put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        else if (repository.get(meal.getId()).getUserId() == authUserId) {
            meal.setUserId(authUserId);
            repository.computeIfPresent(meal.getId(), (k, v) -> meal);
            return meal;
        }
        return null;

    }

    @Override
    public boolean delete(int id, int authUserId) {
        return get(id, authUserId) != null && repository.remove(id) != null;

    }

    @Override
    public Meal get(int id, int authUserId) {
        Meal meal = repository.get(id);
        return meal.getUserId() == authUserId ? meal : null;
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        return repository.values().stream().filter(meal -> meal.getUserId() == userId)
                .sorted((m1, m2) -> m2.getDateTime().toLocalDate()
                        .compareTo(m1.getDateTime().toLocalDate()))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Meal> getAllByFilter(int userId, LocalDate startD, LocalDate endD) {
        return repository.values().stream().filter(meal -> meal.getUserId() == userId)
                .filter(meal -> DateTimeUtil
                        .isBetweenHalfOpen(meal.getDateTime().toLocalDate(), startD, endD))
                .sorted((m1, m2) -> m2.getDateTime().toLocalDate()
                        .compareTo(m1.getDateTime().toLocalDate()))
                .collect(Collectors.toList());
    }

    private void save(Meal meal) {
        meal.setId(counter.incrementAndGet());
        meal.setUserId(1);
        repository.put(meal.getId(), meal);
    }
}

