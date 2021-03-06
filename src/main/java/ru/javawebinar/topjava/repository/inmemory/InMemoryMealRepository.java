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
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Map<Integer, Meal>> repository
            = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> save(meal, 1));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        checkUserEntries(userId);
        int id;
        if (meal.isNew()) {
            id = counter.incrementAndGet();
            meal.setId(id);
        } else {
            id = meal.getId();
        }
        repository.get(userId).put(id, meal);
        return meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        checkUserEntries(userId);
        return repository.get(userId).remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        checkUserEntries(userId);
        return repository.get(userId).get(id);
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        return filterByPredicate(userId, meal -> true);
    }

    @Override
    public Collection<Meal> getAllByFilter(int userId, LocalDate startD, LocalDate endD) {
        return filterByPredicate(userId, meal -> DateTimeUtil
                .isBetweenHalfOpen(meal.getDateTime().toLocalDate(), startD, endD));
    }

    private void checkUserEntries(int userId) {
        if (!repository.containsKey(userId)) {
            repository.put(userId, new ConcurrentHashMap<>());
        }
    }

    private Collection<Meal> filterByPredicate(int userId, Predicate<Meal> filter) {
        checkUserEntries(userId);
        return repository.get(userId).values().stream()
                .filter(filter)
                .sorted((m1, m2) -> m2.getDateTime().toLocalDate()
                        .compareTo(m1.getDateTime().toLocalDate()))
                .collect(Collectors.toList());
    }
}

